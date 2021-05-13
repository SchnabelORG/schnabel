package com.schnabel.schnabel.users.repository;


import com.schnabel.schnabel.users.model.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Allergy Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "allergies", path = "allergy")
public interface IAllergyRepository extends JpaRepository<Allergy, Long> {
}
