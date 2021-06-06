package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.auth.dto.LoginRequest;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.dto.MedicalEmployeeDTO;
import com.schnabel.schnabel.users.dto.MedicalEmployeeDTOAssembler;
import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.model.MedicalEmployee;
import com.schnabel.schnabel.users.service.IMedicalEmployeeService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/medical_employee")
public class MedicalEmployeeController {
    
    private final IMedicalEmployeeService service;
    private final MedicalEmployeeDTOAssembler medicalEmployeeDTOAssembler;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public MedicalEmployeeController(IMedicalEmployeeService service, MedicalEmployeeDTOAssembler medicalEmployeeDTOAssembler, JwtUtils jwtUtils) {
        this.service = service;
        this.medicalEmployeeDTOAssembler = medicalEmployeeDTOAssembler;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("{id}")
    public ResponseEntity<MedicalEmployeeDTO> get(@PathVariable("id") Long id) {
        return service.getDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("jwt")
    public ResponseEntity<MedicalEmployeeDTO> getByJws(@RequestHeader("Authorization") String authHeader) {
        String jws;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jws = authHeader.substring(7, authHeader.length());
        } else {
            return ResponseEntity.badRequest().build();
        }

        String email = jwtUtils.getEmailFromJws(jws);
        return service.findByEmail(email)
                .map(medicalEmployeeDTOAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("pass")
    public ResponseEntity<MedicalEmployeeDTO> changePassword(@RequestHeader("Authorization") String authHeader, @RequestBody LoginRequest dto)
    {
        Optional<MedicalEmployee> medicalEmployee = service.findByEmail(dto.getEmail());
        if(medicalEmployee.isPresent()){
            medicalEmployee.get().setPassword(passwordEncoder.encode(dto.getPassword()));
            medicalEmployee.get().setDefaultPasswordChanged(true);
        }
        service.update(medicalEmployee.get());
        return service.get(medicalEmployee.get().getId()).map(medicalEmployeeDTOAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
