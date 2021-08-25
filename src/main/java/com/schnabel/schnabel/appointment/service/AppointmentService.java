package com.schnabel.schnabel.appointment.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.persistence.NoResultException;

import com.schnabel.schnabel.appointment.dto.AppointmentDTO;
import com.schnabel.schnabel.appointment.dto.AppointmentDTOAssembler;
import com.schnabel.schnabel.appointment.dto.NewAppointmentDTO;
import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.appointment.repository.IAppointmentRepository;
import com.schnabel.schnabel.email.service.IMailService;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;
import com.schnabel.schnabel.users.model.MedicalEmployee;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.dto.ShiftDTO;

import com.schnabel.schnabel.users.model.Vacation;
import com.schnabel.schnabel.users.service.IPharmacistService;
import com.schnabel.schnabel.users.service.IDermatologistService;
import com.schnabel.schnabel.users.service.IPharmacyAdminService;
import com.schnabel.schnabel.users.service.IShiftService;
import com.schnabel.schnabel.users.service.IVacationService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;


/**
 * Appointment service implementation
 */
@Service
public class AppointmentService  extends JpaService<Appointment, Long, IAppointmentRepository> implements IAppointmentService {

    private final AppointmentDTOAssembler dtoAsm;
    private final PagedResourcesAssembler<Appointment> pageAsm;
    private static final long CONSULT_DURATION_MINUTES = 15;
    private final IMailService mailService;
    private final IPharmacistService pharmacistService;
    private final IShiftService shiftService;
    private final IDermatologistService dermatologistService;
    private final IPharmacyAdminService pharmacyAdminService;

    private final IPharmacyService pharmacyService;

    private final IVacationService vacationService;

    @Autowired
    public AppointmentService(IAppointmentRepository repository, AppointmentDTOAssembler dtoAsm, PagedResourcesAssembler<Appointment> pageAsm, IShiftService shiftService, AppointmentDTOAssembler appointmentDTOAssembler, IDermatologistService dermatologistService, IPharmacyAdminService pharmacyAdminService, IMailService mailService,IPharmacyService pharmacyService, IPharmacistService pharmacistService, IVacationService vacationService) {
        super(repository);
        this.dtoAsm = dtoAsm;
        this.pageAsm = pageAsm;
        this.shiftService = shiftService;
        this.dermatologistService = dermatologistService;
        this.pharmacyAdminService = pharmacyAdminService;
        this.mailService = mailService;
        this.pharmacistService = pharmacistService;
        this.pharmacyService = pharmacyService;
        this.vacationService = vacationService;
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
    public Boolean makeNewAppAsPharmacist(NewAppointmentDTO newAppointment, Patient patient) {
        Appointment appointment = new Appointment();
        Period period = new Period();
        period.setStartTime(newAppointment.getStartTime());
        period.setEndTime(newAppointment.getEndTime());
        appointment.setPeriod(period);
        appointment.setPatient(patient);
        MedicalEmployee medicalEmployee = (MedicalEmployee) pharmacistService.get(newAppointment.getPharmacistId()).get();
        appointment.setMedicalEmployee(medicalEmployee);
        Pharmacy pharmacy = pharmacyService.get(newAppointment.getPharmacyId()).get();
        appointment.setPharmacy(pharmacy);
        appointment.setFree(false);
        Optional<Appointment> app = add(appointment);
        if (app.isPresent()){
            this.mailService.sendAppointmentConfirmationMail(patient.getEmail(), app.get());
            return true;
        }
        return false;

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

    /**
    * Pharmacy admin define appointment
    */
    @Override
    public boolean defineAppointment(LocalDateTime startTime, LocalDateTime endTime, double price, Long dermatologistId, String email)
    {
        if(startTime.getDayOfMonth() != endTime.getDayOfMonth())
        {
            return false;
        }
        Long pharmacyId = pharmacyAdminService.findByEmail(email).get().getPharmacy().getId();
        if(checkAvailability(startTime, endTime, dermatologistId, pharmacyId))
        {
            Appointment newAppointment = new Appointment(price, new Period(startTime, endTime), true, dermatologistService.get(dermatologistId).get(), pharmacyAdminService.findByEmail(email).get().getPharmacy());
            Optional<Appointment> appointment = add(newAppointment);
            if(appointment.isPresent())
            {
                return true;
            }
        }
        return false;
    }

    /**
    * Checking availability(Pharmacy admin define appointment)
    */
    private boolean checkAvailability(LocalDateTime startTime, LocalDateTime endTime, Long dermatologistId, Long pharmacyId)
    {
        Optional<ShiftDTO> shift = shiftService.getShiftMedicalEmployeePharmacy(dermatologistId, pharmacyId);
        List<Appointment> appointments = getAllByDermatologistForDay(dermatologistId, startTime);
        List<Vacation> vacations = vacationService.findByMedicalEmployeeId(dermatologistId);

        for (Vacation vacation : vacations) {
            if(startTime.isAfter(vacation.getPeriod().getStartTime()) && endTime.isBefore(vacation.getPeriod().getEndTime())) {
                return false;
            }
        }

        if (startTime.toLocalTime().isAfter(shift.get().getStartTime()) && endTime.toLocalTime().isBefore(shift.get().getEndTime()))
        {
            if(!appointments.isEmpty())
            {
                for (Appointment appointment : appointments) 
                {
                    if(appointment.getPeriod().getStartTime().isBefore(endTime) && startTime.isBefore(appointment.getPeriod().getEndTime()))
                    {
                        return false;
                    }
                }
            }
            return true;
        } 
        else 
        {
            return false;
        }
    }


    @Override
    public Optional<AppointmentDTO> getDTO(Long id) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public PagedModel<AppointmentDTO> getAllbyPharmacist(Pageable pageable, Long pharmacystId) {
        Page<Appointment> appointments = repository.findByMedicalEmployeeId(pageable ,pharmacystId);
        return pageAsm.toModel(appointments, dtoAsm);
    }

    @Override
    @Transactional
    public List<Appointment> getAllByDermatologistForDay(Long dermatologistId, LocalDateTime date) 
    {
        List<Appointment> appointments = repository.findByMedicalEmployeeId(dermatologistId);
        List<Appointment> appointmentsForDay = new ArrayList<Appointment>();
        
        if(!appointments.isEmpty())
        {
            for (Appointment appointment : appointments) 
            {
                if(appointment.getPeriod().getStartTime().getDayOfMonth() == date.getDayOfMonth())
                {
                    appointmentsForDay.add(appointment);
                }    
            }
        }
        return appointmentsForDay;
    }

    public PagedModel<AppointmentDTO> findDermatologistAppointments(Pageable pageable) {
        Page<Appointment> appointments = repository.findDermatologistAppointments(pageable);
        return pageAsm.toModel(appointments, dtoAsm);
    }

    @Override
    public Optional<AppointmentDTO> findByIdDTO(Long id) {
        return get(id).map(dtoAsm::toModel);
    }

    @Override
    @Transactional
    public PagedModel<AppointmentDTO> findFreeDermatologistAppointments(Pageable pageable) {
        try{
            Page<Appointment> appointments = repository.findFreeDermatologistAppointments(pageable);
            return pageAsm.toModel(appointments, dtoAsm);
        } catch (NoResultException ignore) {
            return PagedModel.empty();
        }
    }

    @Override
    @Transactional
    public PagedModel<AppointmentDTO> findByPatientId(Long patientId, Pageable pageable) {
        try {
            Page<Appointment> appointments = repository.findByPatientId(patientId, pageable);
            return pageAsm.toModel(appointments, dtoAsm);
        } catch (NoResultException ignore) {
            return PagedModel.empty();
        }
    }

    @Override
    @Transactional
    public PagedModel<AppointmentDTO> findDermApptByPatientId(Long patientId, Pageable pageable) {
        try {
            Page<Appointment> appointments = repository.findDermApptByPatientId(patientId, pageable);
            return pageAsm.toModel(appointments, dtoAsm);
        } catch (NoResultException ignore) {
            return PagedModel.empty();
        }
    }


    @Override
    @Transactional
    public PagedModel<AppointmentDTO> findDermHistory(Long patientId, Pageable pageable) {
        Page<Appointment> appts = repository.findDermHistory(patientId, pageable);
        return pageAsm.toModel(appts, dtoAsm);
    }


    @Override
    @Transactional
    public PagedModel<AppointmentDTO> findConsultHistory(Long patientId, Pageable pageable) {
        Page<Appointment> appts = repository.findConsultHistory(patientId, pageable);
        return pageAsm.toModel(appts, dtoAsm);
    }


    @Override
    @Transactional
    public PagedModel<AppointmentDTO> findConsultByPatientId(Long patientId, Pageable pageable) {
        Page<Appointment> consults = repository.findConsultByPatientId(patientId, pageable);
        return pageAsm.toModel(consults, dtoAsm);
    }

    @Override
    @Transactional
    public PagedModel<AppointmentDTO> findFreeDermatologistAppointmentsByPharmacy(Long pharmacyId, Pageable pageable)
    {
        try{
            Page<Appointment> appointments = repository.findFreeDermatologistAppointmentsByPharmacy(pharmacyId, pageable);
            return pageAsm.toModel(appointments, dtoAsm);
        } catch (NoResultException ignore) {
            return PagedModel.empty();
        }
    }

    @Override
    @Transactional
    public PagedModel<AppointmentDTO> findByPharmacyId(Long pharmacyId, Pageable pageable)
    {
        try{
            Page<Appointment> appointments = repository.findByPharmacyId(pharmacyId, pageable);
            return pageAsm.toModel(appointments, dtoAsm);
        } catch (NoResultException ignore) {
            return PagedModel.empty();
        }
    }

    @Override
    @Transactional
    public List<Integer> countAppointmentsByMonth(Long pharmacyId, Pageable pageable)
    {
        List<Integer> countMonth = new ArrayList<Integer>();
        List<Appointment> appointments = repository.findByPharmacyId(pharmacyId, pageable).getContent();

        for (int i = 1; i < 13; i++) {
            countMonth.add(count((i), appointments));
        }
        return countMonth;
    }

    private Integer count(Integer month, List<Appointment> appointments)
    {
        int counter = 0;
        for (Appointment appointment : appointments) {
            if(appointment.getPeriod().getEndTime().isBefore(LocalDateTime.now()) && appointment.getPeriod().getStartTime().getYear() == LocalDate.now().getYear() && appointment.getPeriod().getStartTime().getMonthValue() == month)
            {
                counter++;
            }
        }
        return counter;
    }

    @Override
    @Transactional
    public List<Integer> countAppointmentsByYear(Long pharmacyId, Pageable pageable)
    {
        List<Integer> countYear = new ArrayList<Integer>();
        List<Appointment> appointments = repository.findByPharmacyId(pharmacyId, pageable).getContent();

        for (int i = 0; i < 2; i++) {
            countYear.add(countYears((i), appointments));
        }
        return countYear;
    }

    private Integer countYears(Integer year, List<Appointment> appointments)
    {
        int counter = 0;
        for (Appointment appointment : appointments) {
            if(year == 0){
                if(appointment.getPeriod().getEndTime().isBefore(LocalDateTime.now()) && appointment.getPeriod().getStartTime().getYear() == LocalDate.now().getYear() - 1)
                {
                    counter++;
                }
            } else {
                if(appointment.getPeriod().getEndTime().isBefore(LocalDateTime.now()) &&  appointment.getPeriod().getStartTime().getYear() == LocalDate.now().getYear())
                {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    @Transactional
    public List<Double> countIncomeByMonth(Long pharmacyId, Pageable pageable)
    {
        List<Double> countMonth = new ArrayList<Double>();
        List<Appointment> appointments = repository.findByPharmacyId(pharmacyId, pageable).getContent();

        for (int i = 1; i < 13; i++) {
            countMonth.add(countIncomeMonth(i, appointments));
        }

        System.out.println(countMonth.get(5)+ "------------");
        return countMonth;
    }

    private Double countIncomeMonth(Integer month, List<Appointment> appointments)
    {
        double counter = 0;
        for (Appointment appointment : appointments) {
            if(appointment.getPeriod().getEndTime().isBefore(LocalDateTime.now()) && appointment.getPeriod().getStartTime().getYear() == LocalDate.now().getYear() && appointment.getPeriod().getStartTime().getMonthValue() == month)
            {
                counter += appointment.getPrice();
            }
        }
        return counter;
    }

    @Override
    @Transactional
    public List<Double> countIncomeByYear(Long pharmacyId, Pageable pageable)
    {
        List<Double> countYear = new ArrayList<Double>();
        List<Appointment> appointments = repository.findByPharmacyId(pharmacyId, pageable).getContent();

        for (int i = 0; i < 2; i++) {
            countYear.add(countIncomeYears(i, appointments));
        }
        return countYear;
    }

    private Double countIncomeYears(Integer year, List<Appointment> appointments)
    {
        double counter = 0;
        for (Appointment appointment : appointments) {
            if(year == 0){
                if(appointment.getPeriod().getEndTime().isBefore(LocalDateTime.now()) && appointment.getPeriod().getStartTime().getYear() == LocalDate.now().getYear() - 1)
                {
                    counter += appointment.getPrice();
                }
            } else {
                if(appointment.getPeriod().getEndTime().isBefore(LocalDateTime.now()) &&  appointment.getPeriod().getStartTime().getYear() == LocalDate.now().getYear())
                {
                    counter += appointment.getPrice();
                }
            }
        }
        return counter;
    }

    @Override
    @Transactional
    public Double calculateIncome(LocalDate start, LocalDate end, Long pharmacyId, Pageable pageable) 
    {
        List<Appointment> appointments = repository.findByPharmacyId(pharmacyId, pageable).getContent();
        double income = 0;
        for (Appointment appointment : appointments) {
            if(appointment.getPeriod().getStartTime().toLocalDate().isAfter(start) && appointment.getPeriod().getStartTime().toLocalDate().isBefore(end))
            {
                income += appointment.getPrice();
            }
        }
        return income;
    }


    @Override
    public List<Appointment> findMissed(Long patientId) {
        return repository.findMissed(patientId);
    }

}