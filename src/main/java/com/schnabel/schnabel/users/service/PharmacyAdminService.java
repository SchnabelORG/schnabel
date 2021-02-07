package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.repository.IPharmacyAdminRepository;

import org.springframework.stereotype.Service;

@Service
public class PharmacyAdminService extends CrudService<PharmacyAdmin, Long> implements IPharmacyAdminService
{
    public PharmacyAdminService(IPharmacyAdminRepository pharmacyAdminRepository)
    {
        super(pharmacyAdminRepository);
    }

    @Override
    public boolean SetAdminToPharmacy(Long id, Pharmacy pharmacy) {
        PharmacyAdmin pharmacyAdmin = this.get(id);
        if(pharmacyAdmin != null) {
            pharmacyAdmin.setPharmacy(pharmacy);
            this.update(pharmacyAdmin);
            return true;
        } else {
          return false;
        }
    }
}
