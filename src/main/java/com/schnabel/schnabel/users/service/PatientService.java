package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.repository.IPatientRepository;

import org.springframework.stereotype.Service;

@Service
public class PatientService extends CrudService<Patient, Long> implements IPatientService
{

    public PatientService(IPatientRepository patientRepository)
    {
        super(patientRepository);
    }
    
}
