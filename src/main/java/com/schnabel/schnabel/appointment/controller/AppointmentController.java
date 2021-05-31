package com.schnabel.schnabel.appointment.controller;

import com.schnabel.schnabel.appointment.dto.AppointmentDTO;
import com.schnabel.schnabel.appointment.service.IAppointmentService;

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
@RequestMapping("api/appointment")
public class AppointmentController {
    
    private final IAppointmentService service;

    @Autowired
    public AppointmentController(IAppointmentService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<AppointmentDTO> get(@PathVariable("id") Long id) {
        return service.findByIdDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/dermatology")
    public ResponseEntity<PagedModel<AppointmentDTO>> getDermatologicalAppts(Pageable pageable) {
        return new ResponseEntity<>(service.findFreeDermatologistAppointments(pageable), HttpStatus.OK);
    }

}
