package com.schnabel.schnabel.appointment.repository;

import java.time.LocalDateTime;

import com.schnabel.schnabel.appointment.model.Appointment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * Appointment Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "appointments", path = "appointment")
public interface IAppointmentRepository extends JpaRepository<Appointment, Long>
{
    Iterable<Appointment> findByFree(boolean isFree);
    @Query(value = "SELECT a.id, a.price, a.start_time, a.end_time, a.free, a.medical_employee_id, a.pharmacy_id, a.patient_id"
        + " FROM appointments a"
        + " INNER JOIN dermatologists"
        + " ON a.medical_employee_id = dermatologists.id",
        nativeQuery = true)
    Page<Appointment> findDermatologistAppointments(Pageable pageable);
    @Query(value = "SELECT a.id, a.price, a.start_time, a.end_time, a.free, a.medical_employee_id, a.pharmacy_id, a.patient_id"
        + " FROM appointments a"
        + " INNER JOIN dermatologists"
        + " ON a.medical_employee_id = dermatologists.id AND a.free = 'T'",
        nativeQuery = true)
    Page<Appointment> findFreeDermatologistAppointments(Pageable pageable);
    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);

    @Query(value = "SELECT a.id, a.price, a.start_time, a.end_time, a.free, a.medical_employee_id, a.pharmacy_id, a.patient_id"
        + " FROM appointments a"
        + " INNER JOIN dermatologists"
        + " ON a.medical_employee_id = dermatologists.id AND a.patient_id = :patient_id",
        nativeQuery = true)
    Page<Appointment> findDermApptByPatientId(@Param("patient_id") Long patientId, Pageable pageable);

    @Query(value = "SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END"
            + " FROM appointments a"
            + " WHERE a.pharmacy_id = :pharmacy_id"
            + " AND a.medical_employee_id = :pharmacist_id"
            + " AND a.start_time <= :start"
            + " AND a.end_time >= :end",
        nativeQuery = true)
    boolean checkIfExists(@Param("pharmacy_id") Long pharmacyId, @Param("pharmacist_id") Long pharmacistId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
