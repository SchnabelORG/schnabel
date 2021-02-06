package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.pharmacies.model.Term;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.repository.IPatientRepository;

import org.springframework.stereotype.Service;

/**
 * Patient service implementation
 */
@Service
public class PatientService extends CrudService<Patient, Long> implements IPatientService
{

    public PatientService(IPatientRepository patientRepository)
    {
        super(patientRepository);
    }

	@Override
    public boolean addTerm(Patient patient, Term term)
    {
        return patient.addTerm(term)
            && update(patient);
	}

	@Override
    public boolean removeTerm(Patient patient, Term term)
    {
        return patient.removeTerm(term)
            && update(patient);
	}
    
}
