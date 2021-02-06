package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.pharmacies.model.Term;
import com.schnabel.schnabel.users.model.Patient;

/**
 * Patient service interface
 */
public interface IPatientService extends ICrudService<Patient, Long>
{
    /**
     * Add term to patient
     * @param patient Patient to whom to add the term
     * @param term Term to be added
     * @return true if added else false
     */
    boolean addTerm(Patient patient, Term term);
    /**
     * Remove term
     * @param patient Patient from whom the term's removed
     * @param term Term to be removed
     * @return true if removed else false
     */
    boolean removeTerm(Patient patient, Term term);
}
