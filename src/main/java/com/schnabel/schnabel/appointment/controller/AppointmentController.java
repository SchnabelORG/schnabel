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

/**
 * Appointment REST controller
 */
@RestController
@RequestMapping("api/appointment")
public class AppointmentController {
    private final IAppointmentService appointmentService;

    @Autowired
    public AppointmentController(IAppointmentService appointmentService)
    {
        this.appointmentService = appointmentService;
    }

    /**
     * Get all appointments
     * @return Page of Appointment
     */
    @GetMapping
    public ResponseEntity<PagedModel<AppointmentDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(appointmentService.getAllDTO(pageable), HttpStatus.OK);
    }

    /**
     * Get appointment by id
     * @return AppointmentDOT
     */
    @GetMapping("{id}")
    public ResponseEntity<AppointmentDTO> get(@PathVariable long id)
    {
        return appointmentService.getDTO(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("appbyemployye/{id}")
    public ResponseEntity<PagedModel<AppointmentDTO>> getAllByEmployee(@PathVariable long id, Pageable pageable)
    {
        return new ResponseEntity<>(appointmentService.getAllbyPharmacist(pageable, id), HttpStatus.OK);

    }

}
