package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.misc.exceptions.PharmacyAdminAlreadyExistsException;
import com.schnabel.schnabel.misc.exceptions.PharmacyAlreadyExistsException;
import com.schnabel.schnabel.pharmacies.DTO.PharmacyDTO;
import com.schnabel.schnabel.users.DTO.PAdminDTO;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.service.IPharmacyAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/api/pharmacyAdmin/register")
    public ResponseEntity<String> register( @RequestBody PAdminDTO pAdminDTO) {
        try {
            pharmacyAdminService.registerNewPharmacyAdmin(pAdminDTO);
        } catch (PharmacyAdminAlreadyExistsException aex) {
            return new ResponseEntity<>(aex.toString(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Pharamcy admin registered!", HttpStatus.OK);
    }

}
