package com.schnabel.schnabel.pswregistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegistrationController {

    @Autowired
    private IHospitalService hospitalService;

    @GetMapping("/pswapi/{id}")
    ResponseEntity<String> getAPIKey(@PathVariable String id)
    {
        Hospital hospital = hospitalService.get(id);
        return hospital == null ? 
            new ResponseEntity<>(HttpStatus.NOT_FOUND) :
            ResponseEntity.ok(hospital.getApiKey());
    }

    @PostMapping("/pswapi")
    ResponseEntity<String> register(@RequestBody Hospital hospital)
    {
        return hospitalService.add(hospital) ?
            ResponseEntity.ok(hospital.getApiKey()) :
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
