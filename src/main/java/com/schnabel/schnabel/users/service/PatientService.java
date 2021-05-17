package com.schnabel.schnabel.users.service;

import java.util.Optional;

import com.schnabel.schnabel.appointment.service.IAppointmentService;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.repository.IPatientRepository;

import org.springframework.stereotype.Service;

/**
 * Patient service implementation
 */
@Service
public class PatientService extends JpaService<Patient, Long, IPatientRepository> implements IPatientService
{

    private final IAppointmentService appointmentService;

    public PatientService(IPatientRepository patientRepository, IAppointmentService appointmentService)
    {
        super(patientRepository);
        this.appointmentService = appointmentService;
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public boolean scheduleAppointment(Long apptId, String email) {
        Optional<Patient> patient = findByEmail(email);
        if (!patient.isPresent()) {
            return false;
        }
        return appointmentService.scheduleAppointment(apptId, patient.get());
    }
}
