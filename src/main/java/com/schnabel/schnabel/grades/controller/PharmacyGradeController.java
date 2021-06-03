package com.schnabel.schnabel.grades.controller;

import com.schnabel.schnabel.grades.dto.RatingRequest;
import com.schnabel.schnabel.grades.service.IPharmacyGradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * PharmacyGrade REST controller
 */
@RestController
public class PharmacyGradeController 
{
    private final IPharmacyGradeService service;
    @Autowired
    public PharmacyGradeController(IPharmacyGradeService service)
    {
        this.service = service;
    }

    @PostMapping("pharmacy")
    public ResponseEntity<String> ratePharmacy(@RequestBody RatingRequest req, @RequestHeader("Authorization") String auth) {
        return service.ratePharmacy(req, auth) ?
            ResponseEntity.ok("Rated")
            : ResponseEntity.badRequest().build();
    }
}
