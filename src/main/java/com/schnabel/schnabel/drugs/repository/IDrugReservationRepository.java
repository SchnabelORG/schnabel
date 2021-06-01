package com.schnabel.schnabel.drugs.repository;


import com.schnabel.schnabel.drugs.model.DrugReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 * Drug reservation Jpa repository interface
 */

public interface IDrugReservationRepository extends JpaRepository<DrugReservation, Long> {
}
