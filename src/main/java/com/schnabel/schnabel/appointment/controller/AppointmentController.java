package com.schnabel.schnabel.appointment.controller;

import com.schnabel.schnabel.appointment.dto.AppointmentRequest;
import com.schnabel.schnabel.appointment.service.IAppointmentService;
import com.schnabel.schnabel.security.util.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Appointment REST controller
 */
@RestController
@RequestMapping("api/appointment")
public class AppointmentController 
{
    private final IAppointmentService appointmentService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AppointmentController(IAppointmentService appointmentService, JwtUtils jwtUtils)
    {
        this.appointmentService = appointmentService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Create new appointment
     * @param req - Appointment creation request containing required info
     * @return OK if created, else BadRequest
     */
    @PostMapping
    public ResponseEntity<String> defineAppointment(@RequestBody AppointmentRequest req, @RequestHeader("Authorization") String authHeader) {
        String jws = jwtUtils.parseJwtFromAuthorizationHeader(authHeader);
        System.out.println("CONTROLLER -----------------------" + req.getStartTime() + "" +  req.getEndTime() + "" + req.getPrice() + "" + req.getDermatologistId() + "JWT" + jws);

        return appointmentService.defineAppointment(req.getStartTime(), req.getEndTime(), req.getPrice(), req.getDermatologistId(), authHeader) ?
            ResponseEntity.ok("Added")
            : ResponseEntity.badRequest().build();

    }

}
