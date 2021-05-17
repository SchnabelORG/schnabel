package com.schnabel.schnabel.email.controller;

import com.schnabel.schnabel.users.service.IPatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/email")
public class MailController {
    
    private final IPatientService patientService;

    @Autowired
    public MailController(IPatientService patientService) {
        this.patientService = patientService;
    }

    @PutMapping("activate/{token}")
    public ResponseEntity<String> activateMail(@PathVariable("token") String token) {
        patientService.activate(token);
        return ResponseEntity.ok("OK");
    }
}
