package com.schnabel.schnabel.drugs.repository;


import com.schnabel.schnabel.drugs.model.DrugReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Drug reservation Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "drugs_reservations", path = "drugreservation")
public interface IDrugReservationRepository extends JpaRepository<DrugReservation, Long> {
}
