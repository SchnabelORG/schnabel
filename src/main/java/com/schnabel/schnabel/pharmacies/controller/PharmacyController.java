package com.schnabel.schnabel.pharmacies.controller;

import com.schnabel.schnabel.pharmacies.dto.PharmacyCreationDTO;
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

import org.springframework.web.bind.annotation.*;


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

    /**
     *
     * Get pharmacy by id
     * @return Pharmacy
     */
    @GetMapping("{id}")
    public ResponseEntity<PharmacyDTO> get(@PathVariable long id)
    {
        return pharmacyService.getDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     *
     * Get all pharmacies
     * @return Iterable of Patients
     */
    @GetMapping
    public ResponseEntity<PagedModel<PharmacyDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(pharmacyService.getAllDTO(pageable), HttpStatus.OK);
    }

    /**
     * Register new Pharmacy
     * @param creationDTO - Pharmacy registration required info
     * @return OK if registered, else BadRequest
     */
    @PostMapping("register")
    public ResponseEntity<String> registerPharamcy(@RequestBody PharmacyCreationDTO creationDTO)
    {
        return pharmacyService.registerPharmacy(creationDTO) ?
                ResponseEntity.ok("Registered")
                : ResponseEntity.badRequest().build();
    }
}
