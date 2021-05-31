package com.schnabel.schnabel.pharmacies.controller;

import java.time.LocalDateTime;
import java.util.Map;

import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("check/{pharmacyName}")
    public ResponseEntity<?> checkByName(@PathVariable("pharmacyName") String pharmacyName) {
        return pharmacyService.findByName(pharmacyName).isPresent() ?
            ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }

    /**
     * Returns pharmacies that have free pharmacist appointments at date(time)
     */
    @GetMapping("phappt/{date}")
    public ResponseEntity<PagedModel<PharmacyDTO>> getWithPhAppts(@PathVariable("date") LocalDateTime startTime, Pageable pageable) {
        return new ResponseEntity<>(pharmacyService.findByFreePharmacistAppointment(startTime, pageable), HttpStatus.OK);
    }
}
