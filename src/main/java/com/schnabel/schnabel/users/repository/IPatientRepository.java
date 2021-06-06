package com.schnabel.schnabel.users.repository;

import java.util.Optional;

import com.schnabel.schnabel.users.model.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Patient Jpa repository interface
 */
public interface IPatientRepository extends JpaRepository<Patient, Long>
{
    Optional<Patient> findByEmail(String email);

    @Query(value = "SELECT CASE WHEN COUNT(a) > 0"
        + " THEN true ELSE false END"
        + " FROM appointments a"
        + " WHERE a.patient_id = :patient_id"
        + " AND a.pharmacy_id = :pharmacy_id"
        + " AND a.end_time <= CURRENT_TIMESTAMP",
        nativeQuery = true)
    boolean hasHadAppointment(@Param("patient_id") Long patientId, @Param("pharmacy_id") Long pharmacyId);

    @Query(value = "SELECT CASE WHEN COUNT(dr) > 0"
        + " THEN true ELSE false END"
        + " FROM drug_reservations dr"
        + " WHERE dr.reservation_patient_id = :patient_id"
        + " AND dr.pharmacy_reservation_id = :pharmacy_id"
        + " AND dr.end_time <= CURRENT_TIMESTAMP"
        + " AND dr.taken = 'T'",
        nativeQuery = true)
    boolean hasPickedUpDrugs(@Param("patient_id") Long patientId, @Param("pharmacy_id") Long pharmacyId);

    @Query(value = "SELECT CASE WHEN COUNT(a) > 0"
        + " THEN true ELSE false END"
        + " FROM appointments a"
        + " WHERE a.patient_id = :patient_id"
        + " AND a.medical_employee_id = :employee_id"
        + " AND a.end_time <= CURRENT_TIMESTAMP",
        nativeQuery = true)
    boolean hasHadEmployeeAppointment(@Param("patient_id") Long patientId, @Param("employee_id") Long employeeId);

    @Query(value = "SELECT CASE WHEN COUNT(dr) > 0"
        + " THEN true ELSE false END"
        + " FROM drug_reservations dr"
        + " WHERE dr.reservation_patient_id = :patient_id"
        + " AND dr.reserved_drug_id = :drug_id"
        + " AND dr.end_time <= CURRENT_TIMESTAMP"
        + " AND dr.taken = 'T'",
        nativeQuery = true)
    boolean hasPickedUpDrug(@Param("patient_id") Long patientId, @Param("drug_id") Long drugId);
}
