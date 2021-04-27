package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.service.IDermatologistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dermatologist REST controller
 */
@RestController
public class DermatologistController 
{
    private final IDermatologistService dermatologistService;
    @Autowired
    public DermatologistController(IDermatologistService dermatologistService)
    {
        this.dermatologistService = dermatologistService;
    }

    /**
     * Get all dermatologists
     * @return Iterable of Dermatologist
     */
    @GetMapping("/api/dermatologist")
    public ResponseEntity<Iterable<Dermatologist>> getAll()
    {
        Iterable<Dermatologist> dermatologists = dermatologistService.getAll();
        return ResponseEntity.ok(dermatologists);
    }

    /**
     * Get all dermatologists for specific pharmacy
     * @return Iterable of Dermatologist
     */
    @GetMapping("/api/dermatologist/{id}")
    public ResponseEntity<Iterable<Dermatologist>> getAllSpecificPharmacy(@PathVariable long id)
    {
        return ResponseEntity.ok(dermatologistService.getAllSpecificPharmacy(id));
    }
}
