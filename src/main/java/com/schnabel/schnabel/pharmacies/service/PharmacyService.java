package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.dto.PharmacyCreationDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.repository.IPharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PharmacyService extends JpaService<Pharmacy, Long, IPharmacyRepository> implements IPharmacyService{

    @Autowired
    public PharmacyService(IPharmacyRepository repository) {
        super(repository);
    }

    @Override
    public boolean registerPharmacy(PharmacyCreationDTO creationDTO) {
        Pharmacy newPharmacy = new Pharmacy(creationDTO.getName(), creationDTO.getAddress());
        Optional<Pharmacy> pharmacy = add(newPharmacy);
        return pharmacy.isPresent();
    }
}
