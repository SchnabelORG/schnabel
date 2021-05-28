package com.schnabel.schnabel.appointment.service;

import java.time.LocalDateTime;
import java.util.List;

import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.misc.interfaces.IJpaService;

/**
 * Appointment Service interface
 */
public interface IAppointmentService extends IJpaService<Appointment, Long>
{
    public boolean defineAppointment(LocalDateTime startTime, LocalDateTime endTime, double price, Long dermatologistId, String authHeader);
    List<Appointment> getAllByDermatologistForDay(Long dermatologistId, LocalDateTime date);
}