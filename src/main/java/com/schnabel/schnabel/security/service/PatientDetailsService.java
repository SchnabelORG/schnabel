package com.schnabel.schnabel.security.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.repository.IPatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * PatientDetails service layer
 */
@Service
public class PatientDetailsService implements IPatientDetailsService {
    
    private final IPatientRepository repository;

    @Autowired
    public PatientDetailsService(IPatientRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Patient> patient = repository.findByEmail(email);
        if (!patient.isPresent() || !patient.get().isActivated()) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }

        return PatientDetails.build(patient.get());
    }

    

}
