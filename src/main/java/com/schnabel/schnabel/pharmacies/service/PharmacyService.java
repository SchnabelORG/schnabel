package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.exceptions.PharmacyAlreadyExistsException;
import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.pharmacies.DTO.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.repository.IPharmacyRepository;

import com.schnabel.schnabel.users.service.IPharmacyAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Pharmacy service implementation
 */
@Service
public class PharmacyService extends CrudService<Pharmacy, Long> implements IPharmacyService
{
    private final IPharmacyRepository pharmacyRepository;
    @Autowired
    public PharmacyService(IPharmacyRepository pharmacyRepository)
    {
        super(pharmacyRepository);
        this.pharmacyRepository = pharmacyRepository;
    }

    /**
     * Register new pharmacy
     * @param pharmacyDTO
     * @return Registered pharmacy
     * @throws PharmacyAlreadyExistsException
     */
    @Override
    public Pharmacy registerNewPharmacy(PharmacyDTO pharmacyDTO) throws PharmacyAlreadyExistsException {
        Pharmacy pharmacy = this.pharmacyRepository.findByName(pharmacyDTO.getName());
        if(pharmacy != null)
            throw new PharmacyAlreadyExistsException("There is already a pharmacy with that name");
        Pharmacy newPharmacy = new Pharmacy(pharmacyDTO.getName(), pharmacyDTO.getAddress());
        return this.add(newPharmacy);
    }

    @Override
    public Pharmacy findByName(String name) {
        return pharmacyRepository.findByName(name);
    }
}
