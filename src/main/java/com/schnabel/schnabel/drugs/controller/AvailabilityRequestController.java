package com.schnabel.schnabel.drugs.controller;

import com.schnabel.schnabel.drugs.dto.AvailabilityRequestDTO;
import com.schnabel.schnabel.drugs.service.IAvailabilityRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/availabilityrequest")
public class AvailabilityRequestController 
{
    private final IAvailabilityRequestService service;

    @Autowired
    public AvailabilityRequestController(IAvailabilityRequestService availabilityRequestService) {
        this.service = availabilityRequestService;
    }

    @GetMapping("{id}")
    public ResponseEntity<AvailabilityRequestDTO> get(@PathVariable("id") Long id) {
        return service.findByIdDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("pharmacy/{id}")
    public ResponseEntity<PagedModel<AvailabilityRequestDTO>> getAllByPharmacy(@PathVariable("id") Long id, Pageable pageable)
    {
        return new ResponseEntity<>(service.findByPharmacyId(id, pageable), HttpStatus.OK);
    }
}
