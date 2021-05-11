package com.schnabel.schnabel.grades.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.grades.model.DermatologistGrade;
import com.schnabel.schnabel.grades.repository.IDermatologistGradeRepository;

import org.springframework.stereotype.Service;

/**
 * DermatologistGrade service implementation
 */
@Service
public class DermatologistGradeService extends JpaService<DermatologistGrade, Long> implements IDermatologistGradeService
{
    public DermatologistGradeService(IDermatologistGradeRepository repository)
    {
		super(repository);
	}
}
