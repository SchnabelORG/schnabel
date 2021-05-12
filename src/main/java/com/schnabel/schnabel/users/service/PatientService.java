package com.schnabel.schnabel.users.service;

import java.util.Optional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.repository.IPatientRepository;

import org.springframework.stereotype.Service;

/**
 * Patient service implementation
 */
@Service
public class PatientService extends JpaService<Patient, Long, IPatientRepository> implements IPatientService
{
    public PatientService(IPatientRepository patientRepository)
    {
        super(patientRepository);
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
