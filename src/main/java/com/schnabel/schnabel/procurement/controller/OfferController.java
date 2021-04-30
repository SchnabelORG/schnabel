package com.schnabel.schnabel.procurement.controller;

import com.schnabel.schnabel.procurement.model.Offer;
import com.schnabel.schnabel.procurement.service.IOfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Offer REST controller
 */
@RestController
public class OfferController 
{
    private final IOfferService offerService;
    @Autowired
    public OfferController(IOfferService offerService)
    {
        this.offerService = offerService;
    }

    /**
     * Get all offers
     * @return Iterable of Offer
     */
    @GetMapping("/api/offer")
    public ResponseEntity<Iterable<Offer>> getAll()
    {
        Iterable<Offer> offers = offerService.getAll();
        return ResponseEntity.ok(offers);
    }

    /**
     * Get offer by id
     * @return Offer
     */
    @GetMapping("/api/offer/{id}")
    public ResponseEntity<Offer> get(@PathVariable long id)
    {
        Offer offer = offerService.get(id);
        return offer == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(offer);
    }
}