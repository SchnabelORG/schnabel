package com.schnabel.schnabel.grades.repository;

import com.schnabel.schnabel.grades.model.DermatologistGrade;

import org.springframework.data.repository.CrudRepository;

/**
 * DermatologistGrade CRUD repository interface
 */
public interface IDermatologistGradeRepository extends CrudRepository<DermatologistGrade, Long>
{    
}