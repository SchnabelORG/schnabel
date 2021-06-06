package com.schnabel.schnabel.users.controller;

import java.util.List;

import com.schnabel.schnabel.users.dto.AllergyDTO;
import com.schnabel.schnabel.users.service.IAllergyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/allergy")
public class AllergyController {
    
    private final IAllergyService service;

    @Autowired
    public AllergyController(IAllergyService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<String>> getAll() {
        return ResponseEntity.ok(service.findPotentialAllergies());
    }

    @GetMapping("{id}")
    public ResponseEntity<AllergyDTO> get(@PathVariable("id") Long id) {
        return service.findByIdDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("patient")
    public ResponseEntity<PagedModel<AllergyDTO>> getPatientAllergies(@RequestHeader("Authorization") String authHeader, Pageable pageable) {
        return ResponseEntity.ok(service.findByPatientId(authHeader, pageable));
    }

    @PostMapping("patient")
    public ResponseEntity<String> addAllergyToPatient(@RequestHeader("Authorization") String authHeader, @RequestBody Long drugId) {
        return service.addToPatient(authHeader, drugId) ?
            ResponseEntity.ok("Added")
            : ResponseEntity.badRequest().build();
    }

}
