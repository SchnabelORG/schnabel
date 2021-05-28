package com.schnabel.schnabel.users.repository;


import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.model.Pharmacist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Pharmacist Jpa repository interface
 */
public interface IPharmacistRepository extends JpaRepository<Pharmacist, Long>
{
    Optional<Pharmacist> findByEmail(String email);
    Page<Pharmacist> findByPharmacy(Pharmacy pharmacy, Pageable pageable);
}
