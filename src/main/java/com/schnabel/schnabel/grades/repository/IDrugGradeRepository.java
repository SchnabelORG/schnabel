package com.schnabel.schnabel.grades.repository;

import java.util.Optional;

import com.schnabel.schnabel.grades.model.DrugGrade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IDrugGradeRepository extends JpaRepository<DrugGrade, Long> {
    Optional<DrugGrade> findByDrugIdAndPatientId(Long drugId, Long patientId);

    @Query(value = "SELECT AVG(dg.value)"
        + " FROM drug_grades dg"
        + " WHERE dg.drug_id = :drug_id",
        nativeQuery = true)
    double findAvg(@Param("drug_id") Long drugId);
}
