package com.schnabel.schnabel.users.service;

import java.util.Optional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.repository.IPharmacyAdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PharmacyAdminService extends JpaService<PharmacyAdmin, Long, IPharmacyAdminRepository> implements IPharmacyAdminService
{
    @Autowired
    public PharmacyAdminService(IPharmacyAdminRepository pharmacyAdminRepository, IShiftService shiftService)
    {
        super(pharmacyAdminRepository);
    }

    @Override
    public Optional<PharmacyAdmin> findByEmail(String email)
    {
        return repository.findByEmail(email);
    }
}