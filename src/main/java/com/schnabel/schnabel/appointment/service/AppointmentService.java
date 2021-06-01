package com.schnabel.schnabel.appointment.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.schnabel.schnabel.appointment.dto.AppointmentDTO;
import com.schnabel.schnabel.appointment.dto.AppointmentDTOAssembler;
import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.appointment.repository.IAppointmentRepository;
import com.schnabel.schnabel.email.service.IMailService;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.service.IPharmacistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;


/**
 * Appointment JPA service implementation
 */
@Service
public class AppointmentService extends JpaService<Appointment, Long, IAppointmentRepository> implements IAppointmentService {

    private final AppointmentDTOAssembler dtoAsm;
    private final PagedResourcesAssembler<Appointment> pageAsm;
    private static final long CONSULT_DURATION_MINUTES = 15;
    private final IMailService mailService;
    private final IPharmacistService pharmacistService;

    @Autowired
    public AppointmentService(IAppointmentRepository repository, AppointmentDTOAssembler dtoAsm, PagedResourcesAssembler<Appointment> pageAsm, IMailService mailService, IPharmacistService pharmacistService) {
        super(repository);
        this.dtoAsm = dtoAsm;
        this.pageAsm = pageAsm;
        this.mailService = mailService;
        this.pharmacistService = pharmacistService;
    }
    
    @Override
    public Iterable<Appointment> findByFree(boolean isFree) {
        return repository.findByFree(isFree);
    }

    @Override
    public boolean scheduleAppointment(Long id, Patient patient) {
        Optional<Appointment> appointment = get(id);
        if(!appointment.isPresent() || !appointment.get().isFree()) {
            return false;
        }
        appointment.get().setPatient(patient);
        appointment.get().setFree(false);
        
        return update(appointment.get()) && 
            this.mailService.sendAppointmentConfirmationMail(patient.getEmail(), appointment.get());
    }

    @Override
    public boolean scheduleConsult(Patient patient, Long pharmacistId, LocalDateTime start) {
        Optional<Pharmacist> pharmacist = this.pharmacistService.get(pharmacistId);
        if(!pharmacist.isPresent()) {
            return false;
        }
        Pharmacy pharmacy = pharmacist.get().getPharmacy();
        Period period = new Period(start, start.plusMinutes(CONSULT_DURATION_MINUTES));
        Appointment appointment = new Appointment(pharmacy.getConsultPrice(), patient, pharmacy, pharmacist.get(), false, period);
        if (repository.checkIfExists(pharmacy.getId(), pharmacist.get().getId(), period.getStartTime(), period.getEndTime())) {
            return false;
        }
        appointment = add(appointment).get();
        return this.mailService.sendAppointmentConfirmationMail(patient.getEmail(), appointment);
    }

    @Override
    public boolean cancelAppointment(Long id, Long patientId) {
        
        Optional<Appointment> appointment = get(id);
        if (!appointment.isPresent() || !appointment.get().getPatient().getId().equals(patientId)) {
            return false;
        }

        appointment.get().setPatient(null);
        appointment.get().setFree(true);
        return update(appointment.get());
    }

    @Override
    @Transactional
    public PagedModel<AppointmentDTO> findDermatologistAppointments(Pageable pageable) {
        Page<Appointment> appointments = repository.findDermatologistAppointments(pageable);
        return pageAsm.toModel(appointments, dtoAsm);
    }

    @Override
    public Optional<AppointmentDTO> findByIdDTO(Long id) {
        return get(id).map(dtoAsm::toModel);
    }

    @Override
    public PagedModel<AppointmentDTO> findFreeDermatologistAppointments(Pageable pageable) {
        try{
            Page<Appointment> appointments = repository.findFreeDermatologistAppointments(pageable);
            return pageAsm.toModel(appointments, dtoAsm);
        } catch (NoResultException ignore) {
            return PagedModel.empty();
        }
    }

    @Override
    public PagedModel<AppointmentDTO> findByPatientId(Long patientId, Pageable pageable) {
        try {
            Page<Appointment> appointments = repository.findByPatientId(patientId, pageable);
            return pageAsm.toModel(appointments, dtoAsm);
        } catch (NoResultException ignore) {
            return PagedModel.empty();
        }
    }

    @Override
    public PagedModel<AppointmentDTO> findDermApptByPatientId(Long patientId, Pageable pageable) {
        try {
            Page<Appointment> appointments = repository.findDermApptByPatientId(patientId, pageable);
            return pageAsm.toModel(appointments, dtoAsm);
        } catch (NoResultException ignore) {
            return PagedModel.empty();
        }
    }

}
