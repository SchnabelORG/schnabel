package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.repository.IPharmacyRepository;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.repository.IPharmacyAdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PharmacyAdminService extends JpaService<PharmacyAdmin, Long, IPharmacyAdminRepository> implements IPharmacyAdminService
{
    private final IPharmacyRepository pharmacyRepository;

    @Autowired
    public PharmacyAdminService(IPharmacyAdminRepository pharmacyAdminRepository, IPharmacyRepository pharmacyRepository)
    {
        super(pharmacyAdminRepository);
        this.pharmacyRepository = pharmacyRepository;
    }

    @Override
    public Page<PharmacyAdmin> findByPharmacy(Pageable pageable, Long pharmacyId){
        return repository.findByPharmacyId(pageable ,pharmacyId);
    }

    @Override
    public Page<PharmacyAdmin> findWithoutPharmacy(Pageable pageable){
        return repository.findByPharmacyId(pageable,null);
    }

    @Override
    public boolean assignPharmacyAdmin(Long pharmacyAdminId, Long pharmacyId) {
        try {
            PharmacyAdmin padmin = repository.findById(pharmacyAdminId).get();
            Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).get();
            if (padmin != null && pharmacy != null) {
                padmin.setPharmacy(pharmacy);
                return update(padmin);
            }
        } catch (Exception e)
        {
            return false;
        }
        return false;
    }

}
