package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.model.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface IVerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByPatient(Patient patient);

}
