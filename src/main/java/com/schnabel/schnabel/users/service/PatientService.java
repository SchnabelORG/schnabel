package com.schnabel.schnabel.users.service;

import java.util.Optional;

import com.schnabel.schnabel.appointment.dto.AppointmentDTO;
import com.schnabel.schnabel.appointment.service.IAppointmentService;
import com.schnabel.schnabel.auth.service.IRefreshTokenService;
import com.schnabel.schnabel.drugs.service.IDrugReservationService;
import com.schnabel.schnabel.email.service.IMailService;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.users.dto.ConsultRequest;
import com.schnabel.schnabel.users.dto.DrugReservationRequest;
import com.schnabel.schnabel.users.dto.PatientDTO;
import com.schnabel.schnabel.users.model.ERole;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.model.Role;
import com.schnabel.schnabel.users.repository.IPatientRepository;

import com.schnabel.schnabel.users.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Patient service implementation
 */
@Service
public class PatientService extends JpaService<Patient, Long, IPatientRepository> implements IPatientService
{
    private final IAppointmentService appointmentService;
    private final IMailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final IRefreshTokenService refreshTokenService;
    private final IRoleRepository roleRepository;
    private final IDrugReservationService drugResService;
    
    @Autowired
    public PatientService(IPatientRepository patientRepository, IMailService mailService, IRefreshTokenService refreshTokenService, IAppointmentService appointmentService, IDrugReservationService drugResService, IRoleRepository roleRepository)
    {
        super(patientRepository);
        this.appointmentService = appointmentService;
        this.mailService = mailService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.refreshTokenService = refreshTokenService;
        this.drugResService = drugResService;
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public boolean scheduleDermAppt(Long apptId, String email) {
        Optional<Patient> patient = findByEmail(email);
        if (!patient.isPresent()) {
            return false;
        }
        return appointmentService.scheduleAppointment(apptId, patient.get());
    }

    @Override
    public boolean cancelAppointment(Long apptId, String email) {
        Optional<Patient> patient = findByEmail(email);
        if (!patient.isPresent()) {
            return false;
        }

        return appointmentService.cancelAppointment(apptId, patient.get().getId());
    }

    public boolean registerPatient(String name, String surname, String email, String password, Address address,
            String phoneNo) {
        String encodedPassword = passwordEncoder.encode(password);
        Patient newPatient = new Patient(name, surname, email, encodedPassword, address, false, phoneNo);
        Role role = roleRepository.findByName(ERole.ROLE_PATIENT).get();
        newPatient.getRoles().add(role);
        Optional<Patient> patient = add(newPatient);
        if(patient.isPresent()) {
            return sendActivationEmail(email);
        }
        return false;
    }

    private boolean sendActivationEmail(String email) {
        return mailService.sendActivationEmail(email);
    }

    @Override
    public boolean activate(String token) {
        String email = mailService.activate(token);
        if (email.isEmpty()) {
            return false;
        }

        Optional<Patient> patient = findByEmail(email);
        if (!patient.isPresent() || patient.get().isActivated()) {
            return false;
        }
        patient.get().setActivated(true);
        return update(patient.get())
            && refreshTokenService.generate(email).isPresent();
    }

    @Override
    public PagedModel<AppointmentDTO> findAppointments(String email, Pageable pageable) {
        Optional<Patient> patient = findByEmail(email);
        if(!patient.isPresent()) {
            return PagedModel.empty();
        }
        return appointmentService.findByPatientId(patient.get().getId(), pageable);
    }

    @Override
    public PagedModel<AppointmentDTO> findDermAppts(String email, Pageable pageable) {
        Optional<Patient> patient = findByEmail(email);
        if(!patient.isPresent()) {
            return PagedModel.empty();
        }
        return appointmentService.findDermApptByPatientId(patient.get().getId(), pageable);
    }

    @Override
    public boolean scheduleConsult(ConsultRequest req, String email) {
        Optional<Patient> patient = findByEmail(email);
        if (!patient.isPresent()) {
            return false;
        }
        return appointmentService.scheduleConsult(patient.get(), req.getPharmacistId(), req.getStart());
    }

    @Override
    public boolean reserveDrug(DrugReservationRequest req, String email) {
        Optional<Patient> patient = findByEmail(email);
        if(!patient.isPresent()) {
            return false;
        }

        return drugResService.reserveDrug(req, patient.get());
    }

    @Override
    public boolean isAllowedToGradePharmacy(Long patientId, Long pharmacyId) {
        return repository.hasHadAppointment(patientId, pharmacyId)
            || repository.hasPickedUpDrugs(patientId, pharmacyId);
    }

    @Override
    public boolean isAllowedToGradeEmployee(Long patientId, Long employeeId) {
        return repository.hasHadEmployeeAppointment(patientId, employeeId);
    }

    @Override
    public boolean isAllowedToGradeDrug(Long patientId, Long drugId) {
        return repository.hasPickedUpDrug(patientId, drugId);
    }

    @Override
    public PagedModel<AppointmentDTO> findConsults(String email, Pageable pageable) {
        Optional<Patient> patient = findByEmail(email);
        if(!patient.isPresent()) {
            return PagedModel.empty();
        }
        return appointmentService.findConsultByPatientId(patient.get().getId(), pageable);
    }

    @Override
    public boolean update(PatientDTO req) {
        Optional<Patient> patient = get(req.getId());
        patient.get().setName(req.getName());
        patient.get().setSurname(req.getSurname());
        patient.get().setPhoneNo(req.getPhoneNo());
        patient.get().setAddress(req.getAddress());
        return update(patient.get());
    }

}
