package com.schnabel.schnabel.pswregistration.controller;

import java.util.List;

import com.schnabel.schnabel.pswregistration.model.SpecialOffer;
import com.schnabel.schnabel.pswregistration.service.ISpecialOfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpecialOfferController {
    @Autowired
    private ISpecialOfferService specialOfferService;

    @GetMapping("pswapi/specialoffers")
    public ResponseEntity<List<SpecialOffer>> getAll()
    {
        return ResponseEntity.ok(specialOfferService.getAll());
    }
}
