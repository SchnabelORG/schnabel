package com.schnabel.schnabel.grades.controller;

import java.util.Optional;

import com.schnabel.schnabel.grades.model.PharmacistGrade;
import com.schnabel.schnabel.grades.service.IPharmacistGradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * PharmacistGrade REST controller
 */
@RestController
public class PharmacistGradeController 
{
    private final IPharmacistGradeService pharmacistGradeService;
    @Autowired
    public PharmacistGradeController(IPharmacistGradeService pharmacistGradeService)
    {
        this.pharmacistGradeService = pharmacistGradeService;
    }

    /**
     * Get all pharmacistGrades
     * @return Iterable of PharmacistGrade
     */
    @GetMapping("/api/pharmacistgrade")
    public ResponseEntity<Iterable<PharmacistGrade>> getAll()
    {
        Iterable<PharmacistGrade> pharmacistGrades = pharmacistGradeService.getAll();
        return ResponseEntity.ok(pharmacistGrades);
    }

    /**
     * Get pharmacistGrade by id
     * @return PharmacistGrade
     */
    @GetMapping("/api/pharmacistgrade/{id}")
    public ResponseEntity<PharmacistGrade> get(@PathVariable long id)
    {
        Optional<PharmacistGrade> pharmacistGrade = pharmacistGradeService.get(id);
        return pharmacistGrade.isPresent() ?
            ResponseEntity.ok(pharmacistGrade.get())
            : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
