package com.schnabel.schnabel.pharmacies.controller;

import java.util.List;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PharmacyController
{
    private final IPharmacyService _pharmacyService;

    @Autowired
    public PharmacyController(IPharmacyService pharmacyService)
    {
        this._pharmacyService = pharmacyService;
    }

    @GetMapping("/api/pharmacy/{id}")
    public ResponseEntity<Pharmacy> getById(@PathVariable int id)
    {
        Pharmacy pharmacy = _pharmacyService.get(id);
        return pharmacy == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(pharmacy);
    }

    @GetMapping("/api/pharmacy")
    public ResponseEntity<List<Pharmacy>> getAll()
    {
        return ResponseEntity.ok(_pharmacyService.getAll());
    }
}
