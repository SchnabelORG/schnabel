package com.schnabel.schnabel.appointment.repository;

import javax.transaction.Transactional;

import com.schnabel.schnabel.appointment.model.Appointment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    @Transactional
    Page<Appointment> findDermatologistAppointments(Pageable pageable);
}
