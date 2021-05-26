package com.schnabel.schnabel.appointment.repository;

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
    Page<Appointment> findByMedicalEmployeeId(Pageable pageable, Long id);
}
