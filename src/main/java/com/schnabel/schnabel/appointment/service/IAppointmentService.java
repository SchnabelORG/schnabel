package com.schnabel.schnabel.appointment.service;

import java.time.LocalDateTime;

import com.schnabel.schnabel.appointment.dto.AppointmentDTO;
import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.model.MedicalEmployee;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

/**
 * Appointment Service interface
 */
public interface IAppointmentService extends IJpaService<Appointment, Long>
{
    public boolean defineAppointment(LocalDateTime startTime, LocalDateTime endTime, double price, MedicalEmployee medicalEmployee, Pageable pageable);
    PagedModel<AppointmentDTO> getAllByDermatologist(Pageable pageable, Long dermatologistId);
}