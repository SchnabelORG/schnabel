package com.schnabel.schnabel.drugreservations.repository;

import com.schnabel.schnabel.drugreservations.model.DrugReservation;

import org.springframework.data.repository.CrudRepository;

/**
 * Drug reservation repository interface
 */
public interface IDrugReservationRepository extends CrudRepository<DrugReservation, Long>
{
    
}
