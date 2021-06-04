package com.schnabel.schnabel.grades.service;

import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.grades.dto.GradeRequest;
import com.schnabel.schnabel.grades.model.DrugGrade;
import com.schnabel.schnabel.misc.interfaces.IJpaService;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface IDrugGradeService extends IJpaService<DrugGrade, Long> {
    PagedModel<DrugDTO> findGradeable(String authHeader, Pageable pageable);
    boolean gradeDrug(GradeRequest req, String authHeader);
}
