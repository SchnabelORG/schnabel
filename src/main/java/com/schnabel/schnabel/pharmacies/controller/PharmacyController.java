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
 */
@RestController
@RequestMapping("api/pharmacy")
public class PharmacyController
{
    private final IPharmacyService pharmacyService;
    private final PharmacyDTOAssembler pharmacyDTOasm;
    private final PagedResourcesAssembler<Pharmacy> pharmacyPageAsm;

    @Autowired
    public PharmacyController(IPharmacyService pharmacyService, PharmacyDTOAssembler pharmacyDTOasm, PagedResourcesAssembler<Pharmacy> pharmacyPageAsm)
    {
        this.pharmacyService = pharmacyService;
        this.pharmacyDTOasm = pharmacyDTOasm;
        this.pharmacyPageAsm = pharmacyPageAsm;
    }

    /**
     * Get pharmacy based on <b>id</b>
     * @param id Pharmacy id
     * @return OK response with pharmacy with matching <b>id</b> if it exists
     * else NOT_FOUND
     */
    @GetMapping("{id}")
    public ResponseEntity<PharmacyDTO> getById(@PathVariable Long id)
    {
        return pharmacyService.get(id)
            .map(pharmacyDTOasm::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all pharmacies
     * @return OK response containing all pharmacies
     */
    @GetMapping
    public ResponseEntity<PagedModel<PharmacyDTO>> getAll(Pageable pageable)
    {
        Page<Pharmacy> pharmacies = pharmacyService.getAll(pageable);
        PagedModel<PharmacyDTO> pagedModel = pharmacyPageAsm.toModel(pharmacies, pharmacyDTOasm);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }
}
