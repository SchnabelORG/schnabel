package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.service.IPharmacyAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PharmacyAdminController 
{
    private final IPharmacyAdminService pharmacyAdminService;
    
    @Autowired
    public PharmacyAdminController(IPharmacyAdminService pharmacyAdminService)
    {
        this.pharmacyAdminService = pharmacyAdminService;
    }

    @GetMapping("/api/pharmacyAdmin/{id}")
    public ResponseEntity<PharmacyAdmin> get(@PathVariable long id)
    {
        PharmacyAdmin pharmacyAdmin = pharmacyAdminService.get(id);
        return pharmacyAdmin == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(pharmacyAdmin);
    }

    @GetMapping("/api/pharmacyAdmin")
    public ResponseEntity<Iterable<PharmacyAdmin>> getAll()
    {
        return ResponseEntity.ok(pharmacyAdminService.getAll());
    }

}
