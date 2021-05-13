package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.registration.dto.UserDTO;
import com.schnabel.schnabel.misc.exceptions.PatientAlreadyExistsException;
import java.util.Optional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.registration.model.VerificationToken;
import com.schnabel.schnabel.users.repository.IPatientRepository;

import com.schnabel.schnabel.registration.repository.IVerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Patient service implementation
 */
@Service
public class PatientService extends JpaService<Patient, Long, IPatientRepository> implements IPatientService
{
    @Autowired
    private final IVerificationTokenRepository verificationTokenRepository;

    @Autowired
    public PatientService(IPatientRepository patientRepository, IVerificationTokenRepository verificationTokenRepository)
    {
        super(patientRepository);
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public Patient registerNewAccount(UserDTO userDTO) throws PatientAlreadyExistsException {
        if(emailExits(userDTO.getEmail())) {
            throw new PatientAlreadyExistsException("There is an account with that email adress: "
                    + userDTO.getEmail());
        }
        System.out.println(userDTO.toString());

        Patient patient = new Patient(userDTO);
        System.out.println(patient.toString());
        return this.add(patient);

    }

    private boolean emailExits(String email) {
        for (Patient patient : this.getAll()) {
            if(patient.getEmail().equals(email))
                return true;
        }
        return false;
    }

    @Override
    public Patient getPatientByToken(String verificationToken) {
        for (VerificationToken token : verificationTokenRepository.findAll()){
            if(token.getToken().equals(verificationToken))
                return token.getPatient();
        }
        return null;
    }

    @Override
    public void saveRegisteredPatient(Patient patient) {
        this.update(patient);
    }

    @Override
    public void createVerificationToken(Patient patient, String token) {
        VerificationToken myToken = new VerificationToken(token, patient);
        System.out.println(myToken);
        verificationTokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        for (VerificationToken token : verificationTokenRepository.findAll()){
            if(token.getToken().equals(VerificationToken))
                return token;
        }
        return null;
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
