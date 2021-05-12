package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.Pharmacist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Pharmacist Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "pharmacists", path = "pharmacist")
public interface IPharmacistRepository extends JpaRepository<Pharmacist, Long>
{
}
