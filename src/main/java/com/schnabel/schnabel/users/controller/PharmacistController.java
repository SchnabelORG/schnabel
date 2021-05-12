package com.schnabel.schnabel.users.controller;

import java.util.Optional;

import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.service.IPharmacistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<Iterable<Pharmacist>> getAll()
    {
        return ResponseEntity.ok(pharmacistService.getAll());
    }

    /**
     * Get pharmacists by id
     * @return Pharmacist
     */
    @GetMapping("/api/pharmacist/{id}")
    public ResponseEntity<Pharmacist> get(@PathVariable long id)
    {
        Optional<Pharmacist> pharmacist = pharmacistService.get(id);
        return pharmacist.isPresent() ?
            ResponseEntity.ok(pharmacist.get())
            : ResponseEntity.notFound().build();
    }
}