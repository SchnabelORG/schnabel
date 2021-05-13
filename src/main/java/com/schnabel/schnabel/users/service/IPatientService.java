package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.registration.dto.UserDTO;
import com.schnabel.schnabel.misc.exceptions.PatientAlreadyExistsException;
import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.registration.model.VerificationToken;

/**
 * Patient service interface
 */
public interface IPatientService extends IJpaService<Patient, Long>
{
    Patient registerNewAccount(UserDTO userDTO) throws PatientAlreadyExistsException;

    Patient getPatientByToken(String verificationToken);

    void saveRegisteredPatient(Patient patient);

    void createVerificationToken(Patient patient, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    Optional<Patient> findByEmail(String email);
}
