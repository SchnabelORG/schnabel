package com.schnabel.schnabel.users.repository;

import java.util.Optional;

import com.schnabel.schnabel.users.model.MedicalEmployee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IMedicalEmployeeRepository extends JpaRepository<MedicalEmployee, Long> {
    Optional<MedicalEmployee> findByEmail(String email);

    @Query(value = "SELECT ph.id, ph.name, ph.surname, ph.email, ph.password, ph.city, ph.postcode, ph.street, ph.street_no, ph.is_activated, ph.score, PHARMACIST as clazz_"
        + " FROM pharmacists ph"
        + " INNER JOIN appointments a"
        + " ON a.medical_employee_id = ph.id"
        + " WHERE a.patient_id = :patient_id"
        + " AND a.end_time <= CURRENT_TIMESTAMP"
        + " GROUP BY ph.id"
        + " UNION"
        + " SELECT d.id, d.name, d.surname, d.email, d.password, d.city, d.postcode, d.street, d.street_no, d.is_activated, d.score, 0 as clazz_"
        + " FROM dermatologists d"
        + " INNER JOIN appointments a"
        + " ON a.medical_employee_id = d.id"
        + " WHERE a.patient_id = :patient_id"
        + " AND a.end_time <= CURRENT_TIMESTAMP"
        + " GROUP BY d.id",
    nativeQuery = true)
    Page<MedicalEmployee> findGradeable(@Param("patient_id") Long patientId, Pageable pageable);
}
