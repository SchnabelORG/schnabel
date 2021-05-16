package com.schnabel.schnabel.users.service;

import java.util.Optional;

import com.schnabel.schnabel.email.service.IMailService;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.repository.IPatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Patient service implementation
 */
@Service
public class PatientService extends JpaService<Patient, Long, IPatientRepository> implements IPatientService
{
    private final IMailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PatientService(IPatientRepository patientRepository, IMailService mailService)
    {
        super(patientRepository);
        this.mailService = mailService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public boolean registerPatient(String name, String surname, String email, String password, Address address,
            String phoneNo) {
        String encodedPassword = passwordEncoder.encode(password);
        Patient newPatient = new Patient(name, surname, email, encodedPassword, address, false, phoneNo);
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
        return update(patient.get());
    }

}
