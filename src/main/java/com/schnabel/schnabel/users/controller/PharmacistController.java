package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.model.Shift;
import com.schnabel.schnabel.users.service.IPharmacistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Pharmacist REST controller
 */
@RestController
public class PharmacistController
{
    private final IPharmacistService pharmacistService;
    @Autowired
    public PharmacistController(IPharmacistService pharmacistService)
    {
        this.pharmacistService = pharmacistService;
    }

    /**
     * Get all pharmacists
     * @return Iterable of Pharmacist
     */
    @GetMapping("/api/pharmacist")
    public ResponseEntity<String> getAll()
    {
        Shift shift = pharmacistService.get(2L).getShifts().stream().filter(s -> s.getId().equals(1L)).findFirst().orElse(null);
        return ResponseEntity.ok(shift.getId().toString());
    }
}
