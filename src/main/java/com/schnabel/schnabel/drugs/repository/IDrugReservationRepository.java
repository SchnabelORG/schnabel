package com.schnabel.schnabel.drugs.repository;


import com.schnabel.schnabel.drugs.model.DrugReservation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Drug reservation Jpa repository interface
 */

public interface IDrugReservationRepository extends JpaRepository<DrugReservation, Long> {
}
