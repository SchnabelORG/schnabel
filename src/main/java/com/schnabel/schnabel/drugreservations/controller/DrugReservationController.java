package com.schnabel.schnabel.drugreservations.controller;

import com.schnabel.schnabel.drugreservations.model.DrugReservation;
import com.schnabel.schnabel.drugreservations.service.IDrugReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Drug reservation REST controller
 */
@RestController
public class DrugReservationController
{
    private final IDrugReservationService drugReservationService;

    @Autowired
    public DrugReservationController(IDrugReservationService drugReservationService)
    {
        this.drugReservationService = drugReservationService;
    }

    /**
     * Returns all drug reservations
     * @return Iterable of <b>DrugReservation</b>
     */
    @GetMapping("/api/drugreservation")
    public ResponseEntity<Iterable<DrugReservation>> getAll()
    {
        return ResponseEntity.ok(this.drugReservationService.getAll());
    }
}
