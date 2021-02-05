package com.schnabel.schnabel.terms.controller;

import com.schnabel.schnabel.terms.model.Term;
import com.schnabel.schnabel.terms.service.ITermService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Term REST controller
 */
@RestController
public class TermController 
{
    private final ITermService termService;

    @Autowired
    public TermController(ITermService termService)
    {
        this.termService = termService;
    }
    
    /**
     * Get all terms
     * @return Iterable of Term
     */
    @GetMapping("/api/terms")
    public ResponseEntity<Iterable<Term>> getAll()
    {
        return ResponseEntity.ok(termService.getAll());
    }

    /**
     * Define term
     */
    @PostMapping("/api/term")
    public ResponseEntity<Term> add(@RequestBody Term term)
    {
        return termService.add(term) ?
            ResponseEntity.ok(term) : 
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
