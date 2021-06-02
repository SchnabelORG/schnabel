package com.schnabel.schnabel.login.controller;

import java.util.Optional;

import com.schnabel.schnabel.login.dto.LoginDTO;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.service.IPatientService;

import com.schnabel.schnabel.users.service.IPharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/login")
public class LoginController {
    
    private final IPatientService patientService;

    @Autowired
    public LoginController(IPatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        Optional<Patient> patient = patientService.findByEmail(dto.getEmail());
        return patient.isPresent() ?
            ResponseEntity.ok(patient.get().getEmail())
            : ResponseEntity.badRequest().build();
    }
}
