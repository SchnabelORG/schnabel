package com.schnabel.schnabel.pharmacies.repository;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import org.springframework.data.repository.CrudRepository;

public interface IPharmacyRepository extends CrudRepository<Pharmacy, Integer>
{
    
}
