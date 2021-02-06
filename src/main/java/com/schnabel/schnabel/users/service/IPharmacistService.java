package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.pharmacies.model.Term;
import com.schnabel.schnabel.users.model.Pharmacist;

/**
 * Pharmacists service interface
 */
public interface IPharmacistService extends ICrudService<Pharmacist, Long>
{
    /**
     * Add term to pharmacist
     * @param pharmacist Pharmacist to whom to add the term
     * @param term Term to be added
     * @return true if added else false
     */
    boolean addTerm(Pharmacist pharmacist, Term term);
    /**
     * Remove term from pharmacist
     * @param pharmacist Pharmacist from whom to remove the term
     * @param term Term to be removed
     * @return true if removed else false
     */
    boolean removeTerm(Pharmacist pharmacist, Term term);
}
