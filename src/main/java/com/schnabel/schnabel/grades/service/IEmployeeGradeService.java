package com.schnabel.schnabel.grades.service;

import com.schnabel.schnabel.grades.dto.GradeRequest;
import com.schnabel.schnabel.grades.model.EmployeeGrade;
import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.dto.MedicalEmployeeDTO;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface IEmployeeGradeService extends IJpaService<EmployeeGrade, Long> {
    PagedModel<MedicalEmployeeDTO> findGradeable(String authHeader, Pageable pageable);
    boolean gradeEmployee(GradeRequest req, String authHeader);
}
