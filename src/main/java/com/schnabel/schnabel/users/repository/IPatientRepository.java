package com.schnabel.schnabel.users.repository;

import java.util.Optional;

import com.schnabel.schnabel.users.model.Patient;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Patient Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "patients", path = "patient")
public interface IPatientRepository extends JpaRepository<Patient, Long>
{
    @Query("SELECT p FROM patients p WHERE p.email = ?1")
    Optional<Patient> findByEmail(String email);
}
