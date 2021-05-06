package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.MedicalEmployee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Medical employee Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "medicalemployees", path = "medicalemployee")
public interface IMedicalEmployee extends JpaRepository<MedicalEmployee, Long>
{
}
