package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.exceptions.PharmacyAdminAlreadyExistsException;
import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.DTO.PAdminDTO;
import com.schnabel.schnabel.users.model.PharmacyAdmin;

public interface IPharmacyAdminService extends ICrudService<PharmacyAdmin, Long>
{
    PharmacyAdmin SetAdminToPharmacy(String email, Pharmacy pharmacy);

    PharmacyAdmin registerNewPharmacyAdmin(PAdminDTO pAdminDTO) throws PharmacyAdminAlreadyExistsException;
}
