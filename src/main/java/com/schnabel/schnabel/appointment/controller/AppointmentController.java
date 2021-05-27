package com.schnabel.schnabel.appointment.controller;

import com.schnabel.schnabel.appointment.service.IAppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AppointmentController(IAppointmentService appointmentService)
    {
        this.appointmentService = appointmentService;
    }
}
