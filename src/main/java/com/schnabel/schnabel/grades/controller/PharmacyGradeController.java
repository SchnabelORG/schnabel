package com.schnabel.schnabel.grades.controller;

import com.schnabel.schnabel.grades.model.PharmacyGrade;
import com.schnabel.schnabel.grades.service.IPharmacyGradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * PharmacyGrade REST controller
 */
@RestController
public class PharmacyGradeController 
{
    private final IPharmacyGradeService pharmacyGradeService;
    @Autowired
    public PharmacyGradeController(IPharmacyGradeService pharmacyGradeService)
    {
        this.pharmacyGradeService = pharmacyGradeService;
    }

    /**
     * Get all pharmacyGrades
     * @return Iterable of PharmacyGrade
     */
    @GetMapping("/api/pharmacygrade")
    public ResponseEntity<Iterable<PharmacyGrade>> getAll()
    {
        Iterable<PharmacyGrade> pharmacyGrades = pharmacyGradeService.getAll();
        return ResponseEntity.ok(pharmacyGrades);
    }

    /**
     * Get pharmacyGrade by id
     * @return PharmacyGrade
     */
    @GetMapping("/api/pharmacygrade/{id}")
    public ResponseEntity<PharmacyGrade> get(@PathVariable long id)
    {
        PharmacyGrade pharmacyGrade = pharmacyGradeService.get(id);
        return pharmacyGrade == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(pharmacyGrade);
    }
}
