package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.dto.PharmacistDTOAssembler;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.service.IPharmacistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
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
    private final IPharmacistService pharmacistService;
    private final PharmacistDTOAssembler pharmacistDTOAssembler;
    private final PagedResourcesAssembler<Pharmacist> pharmacistPageAsm;

    @Autowired
    public PharmacistController(IPharmacistService pharmacistService, PharmacistDTOAssembler pharmacistDTOAssembler, PagedResourcesAssembler<Pharmacist> pharmacistPageAsm)
    {
        this.pharmacistService = pharmacistService;
        this.pharmacistDTOAssembler = pharmacistDTOAssembler;
        this.pharmacistPageAsm = pharmacistPageAsm;
    }

    /**
     * Get all pharmacists
     * @return Iterable of Pharmacist
     */
    @GetMapping
    public ResponseEntity<PagedModel<PharmacistDTO>> getAll(Pageable pageable)
    {
        Page<Pharmacist> pharmacists = pharmacistService.getAll(pageable);
        PagedModel<PharmacistDTO> pagedModel = pharmacistPageAsm.toModel(pharmacists, pharmacistDTOAssembler);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    /**
     * Get pharmacists by id
     * @return Pharmacist
     */
    @GetMapping("{id}")
    public ResponseEntity<PharmacistDTO> get(@PathVariable Long id)
    {
        return pharmacistService.get(id).map(pharmacistDTOAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("pharmacy/{id}")
    public ResponseEntity<PagedModel<PharmacistDTO>> getByPharmacyId(@PathVariable("id") Long pharmacyId, Pageable pageable) {
        Page<Pharmacist> pharmacists = pharmacistService.findByPharmacy(pharmacyId, pageable);
        PagedModel<PharmacistDTO> pagedModel = pharmacistPageAsm.toModel(pharmacists, pharmacistDTOAssembler);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<PharmacistDTO> put(@RequestBody Pharmacist pharmacist)
    {
        pharmacistService.update(pharmacist);
        return pharmacistService.get(pharmacist.getId()).map(pharmacistDTOAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}