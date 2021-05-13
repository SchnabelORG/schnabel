package com.schnabel.schnabel.grades.repository;

import com.schnabel.schnabel.grades.model.PharmacistGrade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * PharmacistGrade Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "pharmacistgrades", path = "pharmacistgrade")
public interface IPharmacistGradeRepository extends JpaRepository<PharmacistGrade, Long>
{    
}