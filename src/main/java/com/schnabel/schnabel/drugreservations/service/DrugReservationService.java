package com.schnabel.schnabel.drugreservations.service;

import java.time.LocalDate;
import java.util.logging.StreamHandler;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.schnabel.schnabel.drugreservations.model.DrugReservation;
import com.schnabel.schnabel.drugreservations.repository.IDrugReservationRepository;
import com.schnabel.schnabel.misc.implementations.CrudService;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

@Service
public class DrugReservationService extends CrudService<DrugReservation, Integer>
    implements IDrugReservationService
{

    public DrugReservationService(IDrugReservationRepository repository)
    {
		super(repository);
	}

	@Override
    public Iterable<DrugReservation> getValidForDate(LocalDate date)
    {
        return StreamSupport.stream(getAll().spliterator(), false)
            .filter(dr -> dr.isValid(date)).collect(Collectors.toList());
	}
    
}
