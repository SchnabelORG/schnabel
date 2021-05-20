package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.Pharmacist;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Pharmacist Jpa repository interface
 */
public interface IPharmacistRepository extends JpaRepository<Pharmacist, Long>
{
}
