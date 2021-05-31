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
    PagedModel<AppointmentDTO> getDermatologistAppointments(Pageable pageable);
    PagedModel<AppointmentDTO> getFreeDermatologistAppointments(Pageable pageable);
    Optional<AppointmentDTO> getDTO(Long id);
    boolean defineAppointment(LocalDateTime startTime, LocalDateTime endTime, double price, Long dermatologistId, String email);
    List<Appointment> getAllByDermatologistForDay(Long dermatologistId, LocalDateTime date);
}

