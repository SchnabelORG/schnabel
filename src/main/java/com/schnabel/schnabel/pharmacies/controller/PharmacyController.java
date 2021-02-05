package com.schnabel.schnabel.pharmacies.controller;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Pharmacy REST controller
 */
@RestController
public class PharmacyController
{
    private final IPharmacyService pharmacyService;

    @Autowired
    public PharmacyController(IPharmacyService pharmacyService)
    {
        this.pharmacyService = pharmacyService;
    }

    /**
     * Get pharmacy by <b>id</b>
     * @param id ID of queried pharmacy
     * @return 
     * OK with pharmacy if exists
     * else BAD_REQUEST
     */
    @GetMapping("/api/pharmacy/{id}")
    public ResponseEntity<Pharmacy> getById(@PathVariable long id)
    {
        Pharmacy pharmacy = pharmacyService.get(id);
        return pharmacy == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(pharmacy);
    }

    /**
     * Get all pharmacies
     * @return Iterable of Pharmacy
     */
    @GetMapping("/api/pharmacy")
    public ResponseEntity<Iterable<Pharmacy>> getAll()
    {
        return ResponseEntity.ok(pharmacyService.getAll());
    }
}
