package com.schnabel.schnabel.pharmacies.controller;

import java.util.Map;

import com.schnabel.schnabel.pharmacies.dto.PharmacyCreationDTO;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Pharmacy REST controller
 */
@RestController
@RequestMapping("api/pharmacy")
public class PharmacyController
{
    private final IPharmacyService pharmacyService;

    @Autowired
    public PharmacyController(IPharmacyService pharmacyService)
    {
        this.pharmacyService = pharmacyService;
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
        return pharmacyService.getDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("search")
    public ResponseEntity<PagedModel<PharmacyDTO>> filteredSearch(@RequestParam Map<String, String> params, Pageable pageable) {
        return new ResponseEntity<>(pharmacyService.filteredSearch(params, pageable), HttpStatus.OK);
    }

    /**
     * Get all pharmacies
     * @return OK response containing all pharmacies
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
