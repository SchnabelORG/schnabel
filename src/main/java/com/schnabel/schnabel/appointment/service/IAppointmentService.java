package com.schnabel.schnabel.appointment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.schnabel.schnabel.appointment.dto.AppointmentDTO;
import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.model.Patient;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

/**
 * Appointment JPA service interface
 */
public interface IAppointmentService extends IJpaService<Appointment, Long> {
    Iterable<Appointment> findByFree(boolean isFree);
    boolean scheduleAppointment(Long id, Patient patient);
    boolean cancelAppointment(Long id, Long patientId);
    boolean defineAppointment(LocalDateTime startTime, LocalDateTime endTime, double price, Long dermatologistId, String email);
    List<Appointment> getAllByDermatologistForDay(Long dermatologistId, LocalDateTime date);
    PagedModel<AppointmentDTO> findDermatologistAppointments(Pageable pageable);
    PagedModel<AppointmentDTO> findFreeDermatologistAppointments(Pageable pageable);
    PagedModel<AppointmentDTO> findByPatientId(Long patientId, Pageable pageable);
    Optional<AppointmentDTO> findByIdDTO(Long id);
    PagedModel<AppointmentDTO> findDermApptByPatientId(Long patientId, Pageable pageable);
}

