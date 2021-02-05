package com.schnabel.schnabel.users.controller;

import com.amazonaws.Response;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.service.IPharmacistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PharmacistController
{
    private final IPharmacistService pharmacistService;
    @Autowired
    public PharmacistController(IPharmacistService pharmacistService)
    {
        this.pharmacistService = pharmacistService;
    }

    @GetMapping("/api/pharmacist")
    public ResponseEntity<String> test()
    {
        Address addr = new Address("2100", "Novi Sad", "Balzakova", 69);
        Pharmacy pharmacy = new Pharmacy(1L, "Jankovic", addr);
        Pharmacist pharmacist = new Pharmacist(2L, "Farmac", "Farmacevic", "farmac@gmail.com", "farm1234", addr, pharmacy);
        this.pharmacistService.add(pharmacist);
        return ResponseEntity.ok("");
    }
}
