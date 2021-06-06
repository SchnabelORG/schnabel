package com.schnabel.schnabel.grades.repository;

import java.util.Optional;

import com.schnabel.schnabel.grades.model.PharmacyGrade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * PharmacyGrade Jpa repository interface
 */
public interface IPharmacyGradeRepository extends JpaRepository<PharmacyGrade, Long> {
    Optional<PharmacyGrade> findByPatientIdAndPharmacyId(Long patientId, Long pharmacyId);

    @Query(value = "SELECT AVG(pg.value)"
        + " FROM pharmacy_grades pg"
        + " WHERE pg.pharmacy_id = :pharmacy_id",
        nativeQuery = true)
    double findAvg(@Param("pharmacy_id") Long pharmacyId);
}
