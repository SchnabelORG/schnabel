package com.schnabel.schnabel.grades.repository;

import com.schnabel.schnabel.grades.model.PharmacyGrade;

import org.springframework.data.repository.CrudRepository;

/**
 * PharmacyGrade CRUD repository interface
 */
public interface IPharmacyGradeRepository extends CrudRepository<PharmacyGrade, Long>
{    
}
