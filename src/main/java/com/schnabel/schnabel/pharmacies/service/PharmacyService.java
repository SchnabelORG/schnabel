package com.schnabel.schnabel.pharmacies.service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.pharmacies.dto.PharmacySearchDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.repository.IPharmacyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of pharmacy service
 */
@Service
public class PharmacyService extends CrudService<Pharmacy, Integer> implements IPharmacyService
{
    @Autowired
    public PharmacyService(IPharmacyRepository pharmacyRepository)
    {
		  super(pharmacyRepository);
    }

    public Iterable<Pharmacy> search(PharmacySearchDTO dto)
    {
      // TODO(Jovan): Add more critera
      return StreamSupport.stream(getAll().spliterator(), false)
        .filter
        (
          ph ->
          ph.getName().equals(dto.getName())
          && ph.getAddress().equals(dto.getAddress())
        ).collect(Collectors.toList());
    }
}
