package com.schnabel.schnabel.grades.repository;

import java.util.Optional;

import com.schnabel.schnabel.grades.model.PharmacyGrade;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * PharmacyGrade Jpa repository interface
 */
public interface IPharmacyGradeRepository extends JpaRepository<PharmacyGrade, Long> {
    Optional<PharmacyGrade> findByPatientIdAndPharmacyId(Long patientId, Long pharmacyId);
}
