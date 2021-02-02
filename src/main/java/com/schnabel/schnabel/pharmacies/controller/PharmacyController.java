package com.schnabel.schnabel.pharmacies.controller;

import com.schnabel.schnabel.pharmacies.dto.PharmacySearchDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
     * Search for pharmacies matching <b>dto</b> critera
     * @param dto contains search criteria
     * @return OK response containing all pharmacies
     * matching <b>dto</b> search criteria
     */
    @PostMapping("/api/pharmacy/search/")
    public ResponseEntity<Iterable<Pharmacy>> search(PharmacySearchDTO dto)
    {
        return ResponseEntity.ok(pharmacyService.search(dto));
    }

    /**
     * Get pharmacy based on <b>id</b>
     * @param id Pharmacy id
     * @return OK response with pharmacy with matching <b>id</b> if it exists
     * else BAD_REQUEST
     */
    @GetMapping("/api/pharmacy/{id}")
    public ResponseEntity<Pharmacy> getById(@PathVariable int id)
    {
        Pharmacy pharmacy = pharmacyService.get(id);
        return pharmacy == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(pharmacy);
    }

    /**
     * Get all pharmacies
     * @return OK response containing all pharmacies
     */
    @GetMapping("/api/pharmacy")
    public ResponseEntity<Iterable<Pharmacy>> getAll()
    {
        return ResponseEntity.ok(pharmacyService.getAll());
    }
}
