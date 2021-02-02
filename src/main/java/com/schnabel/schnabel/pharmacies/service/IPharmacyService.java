package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.pharmacies.dto.PharmacySearchDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;

/**
 * Pharmacy service interface
 */
public interface IPharmacyService extends ICrudService<Pharmacy, Integer>
{
    /**
     * Searches pharmacies based on <b>dto</b> critera
     * @param dto contains search critera
     * @return Iterable of Pharmacy
     */
    public Iterable<Pharmacy> search(PharmacySearchDTO dto);
}
