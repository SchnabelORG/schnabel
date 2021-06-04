package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.users.dto.MedicalEmployeeDTO;
import com.schnabel.schnabel.users.service.IMedicalEmployeeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/medical_employee")
public class MedicalEmployeeController {
    
    private final IMedicalEmployeeService service;

    public MedicalEmployeeController(IMedicalEmployeeService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<MedicalEmployeeDTO> get(@PathVariable("id") Long id) {
        return service.getDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
