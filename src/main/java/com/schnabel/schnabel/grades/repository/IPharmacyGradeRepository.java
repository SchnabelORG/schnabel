package com.schnabel.schnabel.grades.repository;

import com.schnabel.schnabel.grades.model.PharmacyGrade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * PharmacyGrade Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "pharmacygrades", path = "pharmacygrade")
public interface IPharmacyGradeRepository extends JpaRepository<PharmacyGrade, Long>
{    
}
