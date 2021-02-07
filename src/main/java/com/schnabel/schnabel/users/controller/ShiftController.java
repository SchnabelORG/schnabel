package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.users.model.Shift;
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
    @Autowired
    public ShiftController(IShiftService shiftService)
    {
        this.shiftService = shiftService;
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
