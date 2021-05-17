package com.schnabel.schnabel.users.service;

import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.users.model.Patient;

/**
 * Patient service interface
 */
public interface IPatientService extends IJpaService<Patient, Long>
{
    Optional<Patient> findByEmail(String email);
    boolean scheduleAppointment(Long apptId, String email);
    boolean cancelAppointment(Long apptId, String email);
    boolean registerPatient(String name, String surname, String email, String password, Address address, String phoneNo);
    boolean activate(String token);
}