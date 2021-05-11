package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.repository.IPharmacyAdminRepository;

import org.springframework.stereotype.Service;

@Service
public class PharmacyAdminService extends JpaService<PharmacyAdmin, Long> implements IPharmacyAdminService
{
    public PharmacyAdminService(IPharmacyAdminRepository pharmacyAdminRepository)
    {
        super(pharmacyAdminRepository);
    }
}
