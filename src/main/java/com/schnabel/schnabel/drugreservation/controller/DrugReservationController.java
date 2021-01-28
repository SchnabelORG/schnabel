package com.schnabel.schnabel.drugreservation.controller;

import com.schnabel.schnabel.drugreservation.model.DrugReservation;
import com.schnabel.schnabel.drugreservation.service.IDrugReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DrugReservationController
{
    private final IDrugReservationService drugReservationService;

    @Autowired
    public DrugReservationController(IDrugReservationService drugReservationService)
    {
        this.drugReservationService = drugReservationService;
    }

    @GetMapping("/api/drugreservation")
    public ResponseEntity<Iterable<DrugReservation>> getAll()
    {
        return ResponseEntity.ok(this.drugReservationService.getAll());
    }
}
