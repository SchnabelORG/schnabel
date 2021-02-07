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
    private IPharmacyAdminService iPharmacyAdminService;
    @Autowired
    public PharmacyService(IPharmacyRepository pharmacyRepository, IPharmacyAdminService iPharmacyAdminService)
    {
        super(pharmacyRepository);
        this.iPharmacyAdminService = iPharmacyAdminService;
    }

    /**
     * Register new pharmacy
     * @param pharmacyDTO
     * @return Registered pharmacy
     * @throws PharmacyAlreadyExistsException
     */
    @Override
    public Pharmacy registerNewPharmacy(PharmacyDTO pharmacyDTO) throws PharmacyAlreadyExistsException {
        if(nameExists(pharmacyDTO.getName()))
            throw new PharmacyAlreadyExistsException("There is already a pharmacy with that name");
        Pharmacy pharmacy = new Pharmacy(pharmacyDTO.getName(), pharmacyDTO.getAddress());
        // TODO(Marko): Should this method be called from PharmacyAdmin controller?
        Pharmacy p = this.add(pharmacy);
        if(!this.iPharmacyAdminService.SetAdminToPharmacy(pharmacyDTO.getAdminId(), p))
            return null;
        return p;
    }


    /**
     * Check if pharmacy with that name already exists
     * @param name
     * @return
     */
    private boolean nameExists(String name) {
        for(Pharmacy pharmacy : this.getAll()) {
            if(pharmacy.getName().equals(name))
                return true;
        }
        return false;
    }
}
