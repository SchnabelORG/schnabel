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

@RestController
@RequestMapping("api/pharmacy")
public class PharmacyController {
    
    private final IPharmacyService pharmacyService;
    private final PharmacyDTOAssembler pharmacyDtoAsm;
    private final PagedResourcesAssembler<Pharmacy> pharmacyPageAsm;

    @Autowired
    public PharmacyController(IPharmacyService pharmacyService, PharmacyDTOAssembler pharmacyDtoAsm, PagedResourcesAssembler<Pharmacy> pharmacyPageAsm) {
        this.pharmacyService = pharmacyService;
        this.pharmacyDtoAsm = pharmacyDtoAsm;
        this.pharmacyPageAsm = pharmacyPageAsm;
    }

    @GetMapping("{id}")
    public ResponseEntity<PharmacyDTO> get(@PathVariable("id") Long id) {
        return pharmacyService.get(id)
            .map(pharmacyDtoAsm::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<PagedModel<PharmacyDTO>> getAll(Pageable pageable) {
        Page<Pharmacy> pharmacies = pharmacyService.getAll(pageable);
        PagedModel<PharmacyDTO> collModel = pharmacyPageAsm.toModel(pharmacies, pharmacyDtoAsm);
        return new ResponseEntity<>(collModel, HttpStatus.OK);
    }

}
