package com.schnabel.schnabel.penalty.controller;

import com.schnabel.schnabel.penalty.dto.PenaltyDTO;
import com.schnabel.schnabel.penalty.service.IPenaltyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/penalty")
public class PenaltyController {
    
    private final IPenaltyService service;

    @Autowired
    public PenaltyController(IPenaltyService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<PenaltyDTO> get(@PathVariable("id") Long id) {
        return service.getDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("patient")
    public ResponseEntity<PagedModel<PenaltyDTO>> getPatientPenalties(@RequestHeader("Authorization") String auth, Pageable pageable) {
        return ResponseEntity.ok(service.findCurrentPenalties(auth, pageable));
    }

}
