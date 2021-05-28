package com.schnabel.schnabel.users.service;

import java.util.Optional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.repository.IPharmacistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Pharmacist service implementation
 */
@Service
public class PharmacistService extends JpaService<Pharmacist, Long, IPharmacistRepository> implements IPharmacistService
{

    private final IPharmacyService pharmacyService;

    @Autowired
    public PharmacistService(IPharmacistRepository repository, IPharmacyService pharmacyService)
    {
        super(repository);
        this.pharmacyService = pharmacyService;
    }

    @Override
    public Page<Pharmacist> findByPharmacy(Long pharmacyId, Pageable pageable) {
        Optional<Pharmacy> pharmacy = pharmacyService.get(pharmacyId);
        if(!pharmacy.isPresent()) {
            return Page.empty();
        }
        return repository.findByPharmacy(pharmacy.get(), pageable);
    }


}
