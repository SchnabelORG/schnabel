package com.schnabel.schnabel.specialoffer.controller;

import com.schnabel.schnabel.specialoffer.model.SpecialOffer;
import com.schnabel.schnabel.specialoffer.service.ISpecialOfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/specialoffer")
    public ResponseEntity<SpecialOffer> add(@RequestBody SpecialOffer specialOffer)
    {
        return specialOfferService.add(specialOffer) ?
            ResponseEntity.ok(specialOffer) :
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(Exception exception)
    {
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
