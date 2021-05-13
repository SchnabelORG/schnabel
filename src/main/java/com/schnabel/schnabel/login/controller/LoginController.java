package com.schnabel.schnabel.login.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.schnabel.schnabel.login.dto.LoginDTO;
import com.schnabel.schnabel.security.service.PatientDetails;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.service.IPatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/login")
public class LoginController {
    
    private final IPatientService patientService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public LoginController(IPatientService patientService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.patientService = patientService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jws = jwtUtils.generateJws(auth);

        // PatientDetails patientDetails = (PatientDetails) auth.getPrincipal();
        // List<String> roles = patientDetails.getAuthorities().stream()
        //     .map(item -> item.getAuthority())
        //     .collect(Collectors.toList());

        return ResponseEntity.ok(jws);

        // Optional<Patient> patient = patientService.findByEmail(dto.getEmail());
        // return patient.isPresent() ?
        //     ResponseEntity.ok(patient.get().getEmail())
        //     : ResponseEntity.badRequest().build();
    }
}
