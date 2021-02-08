package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.exceptions.PharmacyAdminAlreadyExistsException;
import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.DTO.PAdminDTO;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.repository.IPharmacyAdminRepository;

import org.springframework.stereotype.Service;

@Service
public class PharmacyAdminService extends CrudService<PharmacyAdmin, Long> implements IPharmacyAdminService
{
    private final IPharmacyAdminRepository pharmacyAdminRepository;
    public PharmacyAdminService(IPharmacyAdminRepository pharmacyAdminRepository)
    {
        super(pharmacyAdminRepository);
        this.pharmacyAdminRepository = pharmacyAdminRepository;
    }

    @Override
    public PharmacyAdmin SetAdminToPharmacy(String email, Pharmacy pharmacy) {
        PharmacyAdmin pharmacyAdmin = pharmacyAdminRepository.findByEmail(email);
        if(pharmacyAdmin != null) {
            pharmacyAdmin.setPharmacy(pharmacy);
            this.update(pharmacyAdmin);
            return pharmacyAdmin;
        } else {
          return null;
        }
    }

    @Override
    public PharmacyAdmin registerNewPharmacyAdmin(PAdminDTO pAdminDTO) throws PharmacyAdminAlreadyExistsException {
        PharmacyAdmin pharmacyAdmin = pharmacyAdminRepository.findByEmail(pAdminDTO.getEmail());
        if(pharmacyAdmin != null)
            throw new PharmacyAdminAlreadyExistsException("There is already a pharmacy admin with that email!");
        PharmacyAdmin newPharmacyAdmin = new PharmacyAdmin(pAdminDTO);
        return this.add(newPharmacyAdmin);
    }
}
