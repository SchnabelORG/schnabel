package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.dto.RegisterPharmacyEmployeeRequest;
import com.schnabel.schnabel.users.service.IDermatologistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Dermatologist REST controller
 */
@RestController
@RequestMapping("api/dermatologist")
public class DermatologistController 
{
    private final IDermatologistService dermatologistService;

    @Autowired
    public DermatologistController(IDermatologistService dermatologistService)
    {
        this.dermatologistService = dermatologistService;
    }

    /**
     * Get all dermatologists
     * @return Page of Dermatologist
     */
    @GetMapping
    public ResponseEntity<PagedModel<DermatologistDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(dermatologistService.getAllDTO(pageable), HttpStatus.OK);
    }

    /**
     * Get dermatologist by id
     * @return Dermatologist
     */
    @GetMapping("{id}")
    public ResponseEntity<DermatologistDTO> get(@PathVariable long id)
    {
        return dermatologistService.getDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("register")
    public ResponseEntity<String> registerPharmacyAdmin(@RequestBody RegisterPharmacyEmployeeRequest request)
    {
        return dermatologistService.registerDermatologist(request.getName(), request.getSurname(), request.getEmail(), request.getPassword(), request.getAddress()) ?
                ResponseEntity.ok("Registered")
                : ResponseEntity.badRequest().build();
    }
}
