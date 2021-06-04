package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.service.IDermatologistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * Get dermatologists by pahrmacy id
     * @return Dermatologist
     */
    @GetMapping("pharmacy/{id}")
    public ResponseEntity<PagedModel<DermatologistDTO>> getAllByPharmacyId(@PathVariable("id") Long pharmacyId, Pageable pageable) 
    {
        return new ResponseEntity<>(dermatologistService.findAllByPharmacy(pharmacyId, pageable), HttpStatus.OK);
    }
}
