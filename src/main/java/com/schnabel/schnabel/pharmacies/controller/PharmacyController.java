package com.schnabel.schnabel.pharmacies.controller;

import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTOAssembler;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Pharmacy REST controller
**/
@RestController
@RequestMapping("api/pharmacy")
public class PharmacyController
{
    private final IPharmacyService pharmacyService;
    private final PharmacyDTOAssembler pharmacyDTOAssembler;
    private final PagedResourcesAssembler<Pharmacy> pharmacyPagedResourcesAssembler;

    @Autowired
    public PharmacyController(IPharmacyService pharmacyService, PharmacyDTOAssembler pharmacyDTOAssembler, PagedResourcesAssembler<Pharmacy> pharmacyPagedResourcesAssembler) {
        this.pharmacyService = pharmacyService;
        this.pharmacyDTOAssembler = pharmacyDTOAssembler;
        this.pharmacyPagedResourcesAssembler = pharmacyPagedResourcesAssembler;
    }

    @GetMapping("{id}")
    public ResponseEntity<PharmacyDTO> get(@PathVariable long id)
    {
        return pharmacyService.get(id).map(pharmacyDTOAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<PagedModel<PharmacyDTO>> getAll(Pageable pageable)
    {
        Page<Pharmacy> patients = pharmacyService.getAll(pageable);
        PagedModel<PharmacyDTO> collModel = pharmacyPagedResourcesAssembler.toModel(patients, pharmacyDTOAssembler);
        return new ResponseEntity<>(collModel, HttpStatus.OK);
    }
}
