package com.schnabel.schnabel.appointment.controller;

import com.schnabel.schnabel.appointment.dto.AppointmentDTO;
import com.schnabel.schnabel.appointment.model.LoyaltyProgram;
import com.schnabel.schnabel.appointment.service.ILoyaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/loyalty")
public class LoyaltyController {

    private final ILoyaltyService service;

    @Autowired
    public LoyaltyController(ILoyaltyService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    ResponseEntity<LoyaltyProgram> getLoyalty() {
        return new ResponseEntity<>(service.get(1L).get(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("update")
    ResponseEntity<String> update(@RequestBody LoyaltyProgram loyaltyProgram) {
        return service.update(loyaltyProgram) ?
                ResponseEntity.ok("Updated") :
                ResponseEntity.badRequest().build();
    }


}
