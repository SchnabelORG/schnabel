package com.schnabel.schnabel.users.repository;

import java.util.Optional;

import com.schnabel.schnabel.users.model.Patient;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Patient Jpa repository interface
 */
public interface IPatientRepository extends JpaRepository<Patient, Long>
{
    Optional<Patient> findByEmail(String email);
}
