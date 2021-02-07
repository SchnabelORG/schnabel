package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.users.DTO.UserDTO;
import com.schnabel.schnabel.misc.exceptions.PatientAlreadyExistsException;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.model.VerificationToken;

/**
 * Patient service interface
 */
public interface IPatientService extends ICrudService<Patient, Long>
{
    Patient registerNewAccount(UserDTO userDTO) throws PatientAlreadyExistsException;

    Patient getPatientByToken(String verificationToken);

    void saveRegisteredPatient(Patient patient);

    void createVerificationToken(Patient patient, String token);

    VerificationToken getVerificationToken(String VerificationToken);
}
