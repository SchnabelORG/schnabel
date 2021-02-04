package com.schnabel.schnabel.pharmacies.controller;

import com.schnabel.schnabel.misc.exceptions.PharmacyAlreadyExistsException;
import com.schnabel.schnabel.pharmacies.DTO.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PharmacyController
{
    private final IPharmacyService pharmacyService;

    @Autowired
    public PharmacyController(IPharmacyService pharmacyService)
    {
        this.pharmacyService = pharmacyService;
    }

    @GetMapping("/api/pharmacy/{id}")
    public ResponseEntity<Pharmacy> getById(@PathVariable int id)
    {
        Pharmacy pharmacy = pharmacyService.get(id);
        return pharmacy == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(pharmacy);
    }

    @GetMapping("/api/pharmacy")
    public ResponseEntity<Iterable<Pharmacy>> getAll()
    {
        return ResponseEntity.ok(pharmacyService.getAll());
    }

    @PostMapping("/api/pharmacy/register")
    public ResponseEntity<String> register(PharmacyDTO pharmacyDTO) {
        try {
            Pharmacy pharmacy = pharmacyService.registerNewPharmacy(pharmacyDTO);
        } catch (PharmacyAlreadyExistsException aex) {
            return new ResponseEntity<>(aex.toString(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Pharamcy registered!", HttpStatus.OK);
    }
}
