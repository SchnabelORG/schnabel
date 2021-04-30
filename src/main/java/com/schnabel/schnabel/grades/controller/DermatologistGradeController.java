package com.schnabel.schnabel.grades.controller;

import com.schnabel.schnabel.grades.model.DermatologistGrade;
import com.schnabel.schnabel.grades.service.IDermatologistGradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * DermatologistGrade REST controller
 */
@RestController
public class DermatologistGradeController 
{
    private final IDermatologistGradeService dermatologistGradeService;
    @Autowired
    public DermatologistGradeController(IDermatologistGradeService dermatologistGradeService)
    {
        this.dermatologistGradeService = dermatologistGradeService;
    }

    /**
     * Get all dermatologistGrades
     * @return Iterable of DermatologistGrade
     */
    @GetMapping("/api/dermatologistgrade")
    public ResponseEntity<Iterable<DermatologistGrade>> getAll()
    {
        Iterable<DermatologistGrade> dermatologistGrades = dermatologistGradeService.getAll();
        return ResponseEntity.ok(dermatologistGrades);
    }

    /**
     * Get dermatologistGrade by id
     * @return DermatologistGrade
     */
    @GetMapping("/api/dermatologistgrade/{id}")
    public ResponseEntity<DermatologistGrade> get(@PathVariable long id)
    {
        DermatologistGrade dermatologistGrade = dermatologistGradeService.get(id);
        return dermatologistGrade == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(dermatologistGrade);
    }
}
