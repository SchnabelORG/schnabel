package com.schnabel.schnabel.pharmacies.service;

import java.util.List;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.repository.IPharmacyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PharmacyService implements IPharmacyService
{
    private IPharmacyRepository _pharmacyRepository;

    @Autowired
    public PharmacyService(IPharmacyRepository pharmacyRepository)
    {
        this._pharmacyRepository = pharmacyRepository;
    }

	@Override
	public boolean add(Pharmacy pharmacy) {
        if(get(pharmacy.getId()) != null) return false;
        _pharmacyRepository.save(pharmacy);
		return true;
	}

	@Override
	public boolean remove(int id) {
        Pharmacy pharmacy = get(id);
        if(pharmacy == null) return false;
        _pharmacyRepository.delete(pharmacy);
		return true;
	}

	@Override
	public boolean update(Pharmacy pharmacy) {
        if(get(pharmacy.getId()) == null) return false;
        _pharmacyRepository.save(pharmacy);
		return true;
	}

	@Override
	public Pharmacy get(int id) {
        return _pharmacyRepository.findById(id).orElse(null);
	}

	@Override
	public List<Pharmacy> getAll() {
        return (List<Pharmacy>) _pharmacyRepository.findAll();
	}
}
