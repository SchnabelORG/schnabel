package com.schnabel.schnabel.grades.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.grades.model.PharmacistGrade;
import com.schnabel.schnabel.grades.repository.IPharmacistGradeRepository;

import org.springframework.stereotype.Service;

/**
 * PharmacistGrade service implementation
 */
@Service
public class PharmacistGradeService extends JpaService<PharmacistGrade, Long> implements IPharmacistGradeService
{
    public PharmacistGradeService(IPharmacistGradeRepository repository)
    {
		super(repository);
	}
}
