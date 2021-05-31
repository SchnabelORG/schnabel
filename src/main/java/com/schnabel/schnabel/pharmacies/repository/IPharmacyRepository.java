package com.schnabel.schnabel.pharmacies.repository;

import java.util.Optional;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Pharmacy CRUD repository interface
 */
public interface IPharmacyRepository extends JpaRepository<Pharmacy, Long>, JpaSpecificationExecutor<Pharmacy>
{
    Optional<Pharmacy> findByName(String name);
}
