package com.schnabel.schnabel.pharmacies.repository;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Pharmacy CRUD repository interface
 */
@RepositoryRestResource(collectionResourceRel = "pharmacies", path = "pharmacy")
public interface IPharmacyRepository extends JpaRepository<Pharmacy, Long>
{

    
}
