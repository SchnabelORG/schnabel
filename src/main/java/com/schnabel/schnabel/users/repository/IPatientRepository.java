package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Patient Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "patients", path = "patient")
public interface IPatientRepository extends JpaRepository<Patient, Long>
{
}
