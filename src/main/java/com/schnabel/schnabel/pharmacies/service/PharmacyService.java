package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.repository.IPharmacyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PharmacyService implements IPharmacyService
{
    private IPharmacyRepository pharmacyRepository;

    @Autowired
    public PharmacyService(IPharmacyRepository pharmacyRepository)
    {
        this.pharmacyRepository = pharmacyRepository;
    }

	@Override
	public boolean add(Pharmacy pharmacy) {
        if(get(pharmacy.getId()) != null) return false;
        pharmacyRepository.save(pharmacy);
		return true;
	}

	@Override
	public boolean remove(Integer id) {
        Pharmacy pharmacy = get(id);
        if(pharmacy == null) return false;
        pharmacyRepository.delete(pharmacy);
		return true;
	}

	@Override
	public boolean update(Pharmacy pharmacy) {
        if(get(pharmacy.getId()) == null) return false;
        pharmacyRepository.save(pharmacy);
		return true;
	}

	@Override
	public Pharmacy get(Integer id) {
        return pharmacyRepository.findById(id).orElse(null);
	}

	@Override
	public Iterable<Pharmacy> getAll() {
        return pharmacyRepository.findAll();
	}
}
