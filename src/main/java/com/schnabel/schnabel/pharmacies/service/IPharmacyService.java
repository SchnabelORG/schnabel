package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.exceptions.PharmacyAlreadyExistsException;
import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.pharmacies.DTO.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;

/**
 * Pharmacy service interface
 */
public interface IPharmacyService extends ICrudService<Pharmacy, Long>
{
    Pharmacy registerNewPharmacy(PharmacyDTO pharmacyDTO) throws PharmacyAlreadyExistsException;
}
