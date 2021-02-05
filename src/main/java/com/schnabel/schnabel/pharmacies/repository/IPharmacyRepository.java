package com.schnabel.schnabel.pharmacies.repository;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import org.springframework.data.repository.CrudRepository;

/**
 * Pharmacy CRUD repository interface
 */
public interface IPharmacyRepository extends CrudRepository<Pharmacy, Long>
{
    
}
