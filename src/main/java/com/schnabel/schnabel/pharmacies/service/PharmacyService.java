package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.exceptions.PharmacyAlreadyExistsException;
import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.pharmacies.DTO.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.repository.IPharmacyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PharmacyService extends CrudService<Pharmacy, Integer> implements IPharmacyService
{
    @Autowired
    public PharmacyService(IPharmacyRepository pharmacyRepository)
    {
		super(pharmacyRepository);
    }

    @Override
    public Pharmacy registerNewPharmacy(PharmacyDTO pharmacyDTO) throws PharmacyAlreadyExistsException {
        if(nameExists(pharmacyDTO.getName()))
            throw new PharmacyAlreadyExistsException("There is already a pharmacy with that name");

        Pharmacy pharmacy = new Pharmacy(pharmacyDTO.getName(), pharmacyDTO.getAddress());
        boolean successful = this.add(pharmacy);
        if(successful)
            return pharmacy;
        else
            return null;
    }

    private boolean nameExists(String name) {
        for(Pharmacy pharmacy : this.getAll()) {
            if(pharmacy.getName().equals(name))
                return true;
        }
        return false;
    }
}
