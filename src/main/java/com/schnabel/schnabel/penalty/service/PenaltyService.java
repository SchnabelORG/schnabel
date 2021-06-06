package com.schnabel.schnabel.penalty.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.schnabel.schnabel.appointment.service.IAppointmentService;
import com.schnabel.schnabel.drugs.service.IDrugReservationService;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.penalty.dto.PenaltyDTO;
import com.schnabel.schnabel.penalty.dto.PenaltyDTOAssembler;
import com.schnabel.schnabel.penalty.model.EPenaltyReason;
import com.schnabel.schnabel.penalty.model.Penalty;
import com.schnabel.schnabel.penalty.repository.IPenaltyRepository;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.service.IPatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PenaltyService extends JpaService<Penalty, Long, IPenaltyRepository> implements IPenaltyService {

    private final PenaltyDTOAssembler dtoAsm;
    private final IPatientService patientService;
    private final PagedResourcesAssembler<Penalty> pageAsm;
    private final JwtUtils jwtUtils;
    private final IDrugReservationService drugReservationService;
    private final IAppointmentService appointmentService;
    @Value("${custom.max_penalties}")
    private int MAX_PENALTIES;

    @Autowired
    public PenaltyService(IPenaltyRepository repository, PenaltyDTOAssembler dtoAsm, IPatientService patientService, JwtUtils jwtUtils, PagedResourcesAssembler<Penalty> pageAsm, IDrugReservationService drugReservationService, IAppointmentService appointmentService) {
        super(repository);
        this.dtoAsm = dtoAsm;
        this.jwtUtils = jwtUtils;
        this.patientService = patientService;
        this.pageAsm = pageAsm;
        this.drugReservationService = drugReservationService;
        this.appointmentService = appointmentService;
    }

    @Override
    public Optional<PenaltyDTO> getDTO(Long id) {
        return get(id).map(dtoAsm::toModel);
    }

    @Override
    public PagedModel<PenaltyDTO> findByPatientId(String authHeader, Pageable pageable) {
        Optional<Long> patientId = verifyPatientAndGetId(authHeader);
        if(!patientId.isPresent()) {
            return PagedModel.empty();
        }
        Page<Penalty> penalties = repository.findByPatientId(patientId.get(), pageable);
        return pageAsm.toModel(penalties, dtoAsm);
    }

    @Override
    @Transactional
    public PagedModel<PenaltyDTO> findCurrentPenalties(String authHeader, Pageable pageable) {
        Optional<Long> patientId = verifyPatientAndGetId(authHeader);
        if(!patientId.isPresent()) {
            return PagedModel.empty();
        }
        Page<Penalty> penalties = repository.findCurrentPenalties(patientId.get(), pageable);
        return pageAsm.toModel(penalties, dtoAsm);
    }

    private Optional<Long> verifyPatientAndGetId(String authHeader) {
        String email = jwtUtils.getEmailFromAuth(authHeader);
        Optional<Patient> patient = patientService.findByEmail(email);
        if(!patient.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(patient.get().getId());
    }

    @Override
    @Transactional
    public boolean generatePenalties(String email) {
        Optional<Patient> patient = patientService.findByEmail(email);
        if(!patient.isPresent()) {
            return false;
        }
        // NOTE(Jovan): No need to generate any more, already has max penalties
        if(repository.countByPatientId(patient.get().getId()) >= MAX_PENALTIES) {
            return true;
        }

        List<Penalty> currPenalties = repository.findCurrentPenalties(patient.get().getId());
        generateMissedDrugReservationPenalties(patient.get(), currPenalties);
        generateMissedAppointmentPenalties(patient.get(), currPenalties);

        return true;
    }

    private void generateMissedAppointmentPenalties(Patient patient, List<Penalty> currPenalties) {
        List<LocalDateTime> missedAppointmentDates = appointmentService.findMissed(patient.getId())
            .stream().map(dr -> dr.getPeriod().getEndTime())
            .collect(Collectors.toList());

        // NOTE(Jovan): Missed appointment penalty dates that are accounted for
        List<LocalDateTime> existingApptPenaltyDates = currPenalties.stream()
            .filter(p -> p.getReason().equals(EPenaltyReason.MISSED_APPOINTMENT))
            .map(p -> p.getDate())
            .collect(Collectors.toList());

        // NOTE(Jovan): Appointment penalties who's dates aren't accounted for
        List<Penalty> newAppointmentPenalties = missedAppointmentDates.stream()
            .filter(d -> !existingApptPenaltyDates.contains(d))
            .map(d -> new Penalty(patient, d, EPenaltyReason.MISSED_APPOINTMENT))
            .collect(Collectors.toList());
        for (Penalty penalty : newAppointmentPenalties) {
            add(penalty);
        }
    }

    private void generateMissedDrugReservationPenalties(Patient patient, List<Penalty> currPenalties) {
        List<LocalDateTime> missedReservationDates = drugReservationService.findMissed(patient.getId())
            .stream().map(dr -> dr.getPeriod().getEndTime())
            .collect(Collectors.toList());

        // NOTE(Jovan): Missed drug reservation penalty dates that are already
        // accounted for
        List<LocalDateTime> existingResPenaltyDates = currPenalties.stream()
            .filter(p -> p.getReason().equals(EPenaltyReason.MISSED_DRUG_RESERVATION))
            .map(p -> p.getDate())
            .collect(Collectors.toList());

        // NOTE(Jovan): Drug reservation penalties who's dates aren't accounted for
        List<Penalty> newDrugReservationPenalties = missedReservationDates.stream()
            .filter(d -> !existingResPenaltyDates.contains(d))
            .map(d -> new Penalty(patient, d, EPenaltyReason.MISSED_DRUG_RESERVATION))
            .collect(Collectors.toList());
        for (Penalty penalty : newDrugReservationPenalties) {
            add(penalty);
        }
    }

    /**
     * Deletes all penalties first of every month
     */
    @Scheduled(cron="0 0 0 1 1/1 *")
    protected void clearPenalties() {
        repository.deleteAll();
    }

    @Override
    public Long countByPatient(String authHeader) {
        Optional<Long> patientId = verifyPatientAndGetId(authHeader);
        if(!patientId.isPresent()) {
            return 0L;
        }

        return repository.countByPatientId(patientId.get());
    }
    
}
