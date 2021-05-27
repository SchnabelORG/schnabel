package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.model.Pharmacist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Pharmacist Jpa repository interface
 */
public interface IPharmacistRepository extends JpaRepository<Pharmacist, Long>
{
    Page<Pharmacist> findByPharmacy(Pharmacy pharmacy, Pageable pageable); 
}
