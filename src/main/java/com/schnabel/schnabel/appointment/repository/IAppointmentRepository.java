package com.schnabel.schnabel.appointment.repository;

import java.util.List;

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

    List<Appointment> findByMedicalEmployeeId(Long id);
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
}
