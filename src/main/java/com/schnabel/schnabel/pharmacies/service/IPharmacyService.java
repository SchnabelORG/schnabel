package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.pharmacies.dto.PharmacyCreationDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;

public interface IPharmacyService extends IJpaService<Pharmacy, Long>
{
    boolean registerPharmacy(PharmacyCreationDTO creationDTO);
}
