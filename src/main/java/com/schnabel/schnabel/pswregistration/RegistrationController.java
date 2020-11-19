package com.schnabel.schnabel.pswregistration;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegistrationController {
    @GetMapping("/pswapi/{id}")
    ResponseEntity<String> getAPIKey(@PathVariable String id)
    {
        // TODO(Jovan): Replace dummy data
        return ResponseEntity.ok(id + "123456789");
    }

    @PostMapping("/pswapi")
    ResponseEntity<Hospital> register(@RequestBody Hospital hospital)
    {
        return ResponseEntity.ok(hospital);
    }
}
