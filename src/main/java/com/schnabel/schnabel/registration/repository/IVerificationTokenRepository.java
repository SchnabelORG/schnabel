package com.schnabel.schnabel.registration.repository;

import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.registration.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByPatient(Patient patient);

}
