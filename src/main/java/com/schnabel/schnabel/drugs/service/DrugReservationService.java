package com.schnabel.schnabel.drugs.service;


import com.schnabel.schnabel.auth.service.IRefreshTokenService;
import com.schnabel.schnabel.drugs.model.DrugReservation;
import com.schnabel.schnabel.drugs.repository.IDrugReservationRepository;
import com.schnabel.schnabel.email.service.IMailService;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.repository.IPatientRepository;
import com.schnabel.schnabel.users.service.IPatientService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Drug reservation service implementation
 */
@Service
public class DrugReservationService extends JpaService<DrugReservation, Long, IDrugReservationRepository> implements IDrugReservationService {

    public DrugReservationService(IDrugReservationRepository drugReservationRepository)
    {
        super(drugReservationRepository);
    }


}
