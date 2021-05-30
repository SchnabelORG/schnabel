package com.schnabel.schnabel.appointment.service;

import java.util.Optional;

import com.schnabel.schnabel.appointment.dto.AppointmentDTO;
import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.misc.interfaces.IJpaService;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import com.schnabel.schnabel.users.model.Patient;

import java.util.Optional;

/**
 * Appointment Service interface
 */
public interface IAppointmentService extends IJpaService<Appointment, Long> {
    Optional<AppointmentDTO> getDTO(Long id);
    PagedModel<AppointmentDTO> getAllbyPharmacist(Pageable pageable, Long pharmacystId);
    Iterable<Appointment> findByFree(boolean isFree);
    boolean scheduleAppointment(Long id, Patient patient);
    boolean cancelAppointment(Long id, Long patientId);
    PagedModel<AppointmentDTO> getDermatologistAppointments(Pageable pageable);
    PagedModel<AppointmentDTO> getFreeDermatologistAppointments(Pageable pageable);
}
