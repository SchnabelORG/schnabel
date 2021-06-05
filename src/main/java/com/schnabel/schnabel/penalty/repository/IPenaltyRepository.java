package com.schnabel.schnabel.penalty.repository;

import java.util.List;

import com.schnabel.schnabel.penalty.model.Penalty;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPenaltyRepository extends JpaRepository<Penalty, Long> {
    Page<Penalty> findByPatientId(Long patientId, Pageable pageable);

    @Query(value = "SELECT p.*"
        + " FROM penalties p"
        + " WHERE DATE_TRUNC('month', p.date) = DATE_TRUNC('month', CURRENT_TIMESTAMP)"
        + " AND p.patient_id = :patient_id",
        nativeQuery = true)
    Page<Penalty> findCurrentPenalties(@Param("patient_id") Long patientId, Pageable pageable);
    @Query(value = "SELECT p.*"
        + " FROM penalties p"
        + " WHERE DATE_TRUNC('month', p.date) = DATE_TRUNC('month', CURRENT_TIMESTAMP)"
        + " AND p.patient_id = :patient_id",
        nativeQuery = true)
    List<Penalty> findCurrentPenalties(@Param("patient_id") Long patientId);
    long countByPatientId(Long patientId);
}
