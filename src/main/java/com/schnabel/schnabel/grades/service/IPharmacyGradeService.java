package com.schnabel.schnabel.grades.service;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.grades.dto.RatingRequest;
import com.schnabel.schnabel.grades.model.PharmacyGrade;

/**
 * PharmacyGrade Service interface
 */
public interface IPharmacyGradeService extends IJpaService<PharmacyGrade, Long> {
    /**
     * Creates a new or updates an existing PharmacyGrade
     */
    boolean ratePharmacy(RatingRequest req, String authHeader);
}
