package com.schnabel.schnabel.grades.repository;

import com.schnabel.schnabel.grades.model.PharmacistGrade;

import org.springframework.data.repository.CrudRepository;

/**
 * PharmacistGrade CRUD repository interface
 */
public interface IPharmacistGradeRepository extends CrudRepository<PharmacistGrade, Long>
{    
}