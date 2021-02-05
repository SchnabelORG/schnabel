package com.schnabel.schnabel.pswregistration.controller;

import com.schnabel.schnabel.pswregistration.model.Hospital;
import com.schnabel.schnabel.pswregistration.model.HospitalDTO;
import com.schnabel.schnabel.pswregistration.service.IHospitalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegistrationController
{
    private final IHospitalService hospitalService;

    @Autowired
    public RegistrationController(IHospitalService hospitalService)
    {
        this.hospitalService = hospitalService;
    }

    @GetMapping("/pswapi/{api}")
    public ResponseEntity<String> getByAPIKey(@PathVariable String api)
    {
        Hospital hospital = hospitalService.get(api);
        return hospital == null ? 
            new ResponseEntity<>(HttpStatus.NOT_FOUND) :
            ResponseEntity.ok(hospital.getName());
    }

    @PostMapping("/pswapi")
    public ResponseEntity<String> register(@RequestBody HospitalDTO hospital)
    {
        return hospitalService.add(new Hospital(hospital.getName(), hospital.getApiKey())) ?
            ResponseEntity.ok(hospital.getApiKey()) :
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException exception)
    {
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
