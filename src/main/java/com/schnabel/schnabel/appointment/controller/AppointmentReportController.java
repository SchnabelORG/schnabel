package com.schnabel.schnabel.appointment.controller;


import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.appointment.model.AppointmentReport;
import com.schnabel.schnabel.appointment.service.IAppointmentReportService;
import com.schnabel.schnabel.appointment.service.IAppointmentService;
import com.schnabel.schnabel.security.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * AppointmentReport REST controller
 */
@RestController
@RequestMapping("api/reportappointment")
public class AppointmentReportController {
    private final IAppointmentReportService reportService;
    private final IAppointmentService appService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AppointmentReportController(IAppointmentReportService reportService, IAppointmentService appService, JwtUtils jwtUtils) {
        this.reportService = reportService;
        this.appService = appService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/addreport/{appId}")
    public ResponseEntity<Boolean> defineAppointment(@PathVariable long appId, @RequestBody String textReport)
    {
        Optional<Appointment> appointment = appService.get(appId);
        if(appointment.isPresent()){
            AppointmentReport appointmentReport = new AppointmentReport();
            appointmentReport.setAppointment(appointment.get());
            appointmentReport.setText(textReport);
            Optional<AppointmentReport> report = reportService.add(appointmentReport);
            if(report.isPresent()){
                return ResponseEntity.ok(Boolean.TRUE);
            }
            return  ResponseEntity.ok(Boolean.FALSE);
        }
        return  ResponseEntity.ok(Boolean.FALSE);
    }
}
