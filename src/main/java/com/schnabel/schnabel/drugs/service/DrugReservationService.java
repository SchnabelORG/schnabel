package com.schnabel.schnabel.drugs.service;

import com.schnabel.schnabel.drugs.model.DrugReservation;
import com.schnabel.schnabel.drugs.repository.IDrugReservationRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
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
