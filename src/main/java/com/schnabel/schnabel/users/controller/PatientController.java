package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.service.IPatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Patient REST controller
 */
@RestController
public class PatientController
{
    private final IPatientService patientService;

    @Autowired
    public PatientController(IPatientService patientService)
    {
        this.patientService = patientService;
    }

    /**
     * Get patient by id
     * @return Patient
     */
    @GetMapping("/api/patient/{id}")
    public ResponseEntity<Patient> get(@PathVariable long id)
    {
        Patient patient = patientService.get(id);
        return patient == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(patient);
    }

    /**
     * Get all patients
     * @return Iterable of Patients
     */
    @GetMapping("/api/patient")
    public ResponseEntity<Iterable<Patient>> getAll()
    {
        return ResponseEntity.ok(patientService.getAll());
    }
}
