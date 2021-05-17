package com.schnabel.schnabel.appointment.service;

import java.util.Optional;

import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.appointment.repository.IAppointmentRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.users.model.Patient;

import org.springframework.stereotype.Service;


/**
 * Appointment JPA service implementation
 */
@Service
public class AppointmentService extends JpaService<Appointment, Long, IAppointmentRepository> implements IAppointmentService {

    public AppointmentService(IAppointmentRepository repository) {
        super(repository);
    }
    
    @Override
    public Iterable<Appointment> findByFree(boolean isFree) {
        return repository.findByFree(isFree);
    }

    @Override
    public boolean scheduleAppointment(Long id, Patient patient) {
        Optional<Appointment> appointment = get(id);
        if(!appointment.isPresent() || !appointment.get().isFree()) {
            return false;
        }

        appointment.get().setPatient(patient);
        appointment.get().setFree(false);
        return update(appointment.get());
    }

    @Override
    public boolean cancelAppointment(Long id, Long patientId) {
        
        Optional<Appointment> appointment = get(id);
        if (!appointment.isPresent() || !appointment.get().getPatient().getId().equals(patientId)) {
            return false;
        }

        appointment.get().setPatient(null);
        appointment.get().setFree(true);
        return update(appointment.get());
    }

}
