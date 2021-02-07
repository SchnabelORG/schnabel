package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.model.PharmacyAdmin;

public interface IPharmacyAdminService extends ICrudService<PharmacyAdmin, Long>
{
    boolean SetAdminToPharmacy(Long id, Pharmacy pharmacy);
}
