package com.schnabel.schnabel.users.controller;

import java.time.LocalTime;

import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.service.IDermatologistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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
    @GetMapping("/api/dermatologists")
    public ResponseEntity<Iterable<Dermatologist>> getAll()
    {
        LocalTime startTime = LocalTime.of(8,0);
        LocalTime endTime = LocalTime.of(12,0);

        LocalTime currentTime = startTime;

        while(currentTime.plusMinutes(30).compareTo(endTime) <= 0)
        {
            System.out.println(currentTime + " " + currentTime.plusMinutes(30));
            currentTime = currentTime.plusMinutes(30);
        }


        Iterable<Dermatologist> dermatologists = dermatologistService.getAll();
        return ResponseEntity.ok(dermatologists);
    }

    /**
     * Get all dermatologists for specific pharmacy
     * @return Iterable of Dermatologist
     */
    @GetMapping("/api/dermatologists/{id}")
    public ResponseEntity<Iterable<Dermatologist>> getAllSpecificPharmacy(@PathVariable long id)
    {
        return ResponseEntity.ok(dermatologistService.getAllSpecificPharmacy(id));
    }
}
