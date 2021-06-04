package com.schnabel.schnabel.grades.service;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.schnabel.schnabel.grades.dto.GradeRequest;
import com.schnabel.schnabel.grades.model.PharmacyGrade;

/**
 * PharmacyGrade Service interface
 */
public interface IPharmacyGradeService extends IJpaService<PharmacyGrade, Long> {
    /**
     * Creates a new or updates an existing PharmacyGrade
     */
    boolean ratePharmacy(GradeRequest req, String authHeader);
    PagedModel<PharmacyDTO> findGraded(String authHeader, Pageable pageable);
    PagedModel<PharmacyDTO> findGradeable(String authHeader, Pageable pageable);
}
