package com.schnabel.schnabel.users.controller;

import java.time.LocalTime;

import com.schnabel.schnabel.pharmacies.service.IPharmacyService;
import com.schnabel.schnabel.users.model.Shift;
import com.schnabel.schnabel.users.repository.IDermatologistRepository;
import com.schnabel.schnabel.users.service.IDermatologistService;
import com.schnabel.schnabel.users.service.IShiftService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Shift REST Controller 
 */
@RestController
public class ShiftController 
{
    private final IShiftService shiftService;
    private final IPharmacyService pharmacyService;
    private final IDermatologistService dermatologistService;
    @Autowired
    public ShiftController(IShiftService shiftService, IPharmacyService pharmacyService, IDermatologistService dermatologistService)
    {
        this.shiftService = shiftService;
        this.pharmacyService = pharmacyService;
        this.dermatologistService = dermatologistService;
    }    

    /**
     * Get all shifts
     * @return Iterable of Shift
     */
    @GetMapping("/api/shifts")
    public ResponseEntity<Iterable<Shift>> getAll()
    {
        Iterable<Shift> shifts = shiftService.getAll();
        return ResponseEntity.ok(shifts);
    }

    /**
     * Add new shift
     * @return Shift
     */
    @PostMapping("/api/shift")
    public ResponseEntity<Shift> add(@RequestBody Shift shift)
    {
        return shiftService.add(shift) != null ?
            ResponseEntity.ok(shift) :
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
