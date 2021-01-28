package com.schnabel.schnabel.specialoffer.controller;

import java.util.List;

import com.schnabel.schnabel.specialoffer.model.SpecialOffer;
import com.schnabel.schnabel.specialoffer.service.ISpecialOfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpecialOfferController {

    private final ISpecialOfferService specialOfferService;

    @Autowired
    public SpecialOfferController(ISpecialOfferService specialOfferService)
    {
        this.specialOfferService = specialOfferService;
    }

    @GetMapping("pswapi/specialoffers")
    public ResponseEntity<Iterable<SpecialOffer>> getAll()
    {
        return ResponseEntity.ok(specialOfferService.getAll());
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(Exception exception)
    {
        return new ResponseEntity<Exception>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
