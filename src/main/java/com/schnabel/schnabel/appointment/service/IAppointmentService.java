package com.schnabel.schnabel.appointment.service;

import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.model.Patient;

/**
 * Appointment JPA service interface
 */
public interface IAppointmentService extends IJpaService<Appointment, Long> {
    Iterable<Appointment> findByFree(boolean isFree);
    boolean scheduleAppointment(Long id, Patient patient);
}
