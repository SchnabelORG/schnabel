package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.users.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

public class RegistrationController {

    @Autowired
    private IPatientService patientService;

}
