package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.Patient;

import org.springframework.data.repository.CrudRepository;

/**
 * Patient CRUD repository interface
 */
public interface IPatientRepository extends CrudRepository<Patient, Long>
{
}
