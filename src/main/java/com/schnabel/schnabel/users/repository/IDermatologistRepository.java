package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.Dermatologist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * Dermatologist Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "dermatologists", path = "dermatologist")
public interface IDermatologistRepository extends JpaRepository<Dermatologist, Long>
{
}