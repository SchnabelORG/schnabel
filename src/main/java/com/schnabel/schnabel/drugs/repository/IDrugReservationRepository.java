package com.schnabel.schnabel.drugs.repository;


import java.util.List;

import com.schnabel.schnabel.drugs.model.DrugReservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Drug reservation Jpa repository interface
 */

public interface IDrugReservationRepository extends JpaRepository<DrugReservation, Long> {
    
    @Query("SELECT dr FROM DrugReservation dr WHERE reserved_drug_id = :drug_id AND pharmacy_reservation_id = :pharmacy_id AND taken = 'false'")
    List<DrugReservation> findNotTakenByDrugAndPharmacy(@Param("drug_id") Long drugId,@Param("pharmacy_id") Long pharmacyId);

    Page<DrugReservation> findByPatientId(Long patientId, Pageable pageable);
    List<DrugReservation> findByPharmacyId(Long pharmacyId);
}
