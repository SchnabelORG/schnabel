package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.users.dto.FreePharmacistLookupRequest;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.service.IPharmacistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Pharmacist REST controller
 */
@RestController
@RequestMapping("api/pharmacist")
public class PharmacistController
{
    private final IPharmacistService service;

    @Autowired
    public PharmacistController(IPharmacistService service)
    {
        this.service = service;
    }

    /**
     * Get all pharmacists
     * @return Iterable of Pharmacist
     */
    @GetMapping
    public ResponseEntity<PagedModel<PharmacistDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(service.findAllDTO(pageable), HttpStatus.OK);
    }

    /**
     * Get pharmacists by id
     * @return Pharmacist
     */
    @GetMapping("{id}")
    public ResponseEntity<PharmacistDTO> get(@PathVariable Long id)
    {
        return service.findbyIdDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get pharmacists with free appointments via pharmacy id
     */
    @PostMapping("free_pharmacy_appt")
    public ResponseEntity<PagedModel<PharmacistDTO>> getFreeByPharmacyId(@RequestBody FreePharmacistLookupRequest req, Pageable pageable) {
        return new ResponseEntity<>(service.findFreeByPharmacy(req, pageable), HttpStatus.OK);
    }

    @GetMapping("pharmacy/{id}")
    public ResponseEntity<PagedModel<PharmacistDTO>> getByPharmacyId(@PathVariable("id") Long pharmacyId, Pageable pageable) {
        return new ResponseEntity<>(service.findByPharmacy(pharmacyId, pageable), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<PharmacistDTO> put(@RequestBody Pharmacist pharmacist)
    {
        return service.updateDTO(pharmacist)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}