package com.schnabel.schnabel.drugreservation.service;

import com.schnabel.schnabel.drugreservation.model.DrugReservation;
import com.schnabel.schnabel.drugreservation.repository.IDrugReservationRepository;
import com.schnabel.schnabel.misc.implementations.CrudService;

import org.springframework.stereotype.Service;

@Service
public class DrugReservationService extends CrudService<DrugReservation, Integer>
    implements IDrugReservationService
{

    public DrugReservationService(IDrugReservationRepository repository)
    {
		super(repository);
	}
    
}
