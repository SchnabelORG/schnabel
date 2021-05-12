package com.schnabel.schnabel.grades.repository;

import com.schnabel.schnabel.grades.model.DermatologistGrade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * DermatologistGrade Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "dermatologistgrades", path = "dermatologistgrade")
public interface IDermatologistGradeRepository extends JpaRepository<DermatologistGrade, Long>
{    
}