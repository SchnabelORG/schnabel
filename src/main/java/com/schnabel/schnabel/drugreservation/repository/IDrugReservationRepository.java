package com.schnabel.schnabel.drugreservation.repository;

import com.schnabel.schnabel.drugreservation.model.DrugReservation;

import org.springframework.data.repository.CrudRepository;

public interface IDrugReservationRepository extends CrudRepository<DrugReservation, Integer>
{
    
}
