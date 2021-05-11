package com.schnabel.schnabel.grades.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.grades.model.PharmacyGrade;
import com.schnabel.schnabel.grades.repository.IPharmacyGradeRepository;

import org.springframework.stereotype.Service;

/**
 * PharmacyGrade service implementation
 */
@Service
public class PharmacyGradeService extends JpaService<PharmacyGrade, Long> implements IPharmacyGradeService
{
    public PharmacyGradeService(IPharmacyGradeRepository repository)
    {
		super(repository);
	}
}
