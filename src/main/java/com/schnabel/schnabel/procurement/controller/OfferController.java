package com.schnabel.schnabel.procurement.controller;

import java.util.Optional;

import com.schnabel.schnabel.procurement.dto.OfferCreationDTO;
import com.schnabel.schnabel.procurement.dto.OfferDTO;
import com.schnabel.schnabel.procurement.model.Offer;
import com.schnabel.schnabel.procurement.service.IOfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Offer REST controller
 */
@RestController
@RequestMapping("api/offer")
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
    @GetMapping()
    public ResponseEntity<Iterable<Offer>> getAll()
    {
        Iterable<Offer> offers = offerService.getAll();
        return ResponseEntity.ok(offers);
    }

    /**
     * Get offer by id
     * @return Offer
     */
    @GetMapping("{id}")
    public ResponseEntity<Offer> get(@PathVariable long id)
    {
        Optional<Offer> offer = offerService.get(id);
        return offer.isPresent() ?
            ResponseEntity.ok(offer.get())
            : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("supplier/{id}")
    public ResponseEntity<PagedModel<OfferDTO>> getBySupplier(Pageable pageable, @PathVariable long id)
    {
        return new ResponseEntity<>(offerService.findBySupplier(pageable, id), HttpStatus.OK);
    }

    @PostMapping("makeoffer")
    public ResponseEntity<String> createOffer(@RequestBody OfferCreationDTO creationDTO)
    {
        return offerService.createOffer(creationDTO.getPrice(), creationDTO.getDateOfDelivery(), creationDTO.getOrderId()) ?
                ResponseEntity.ok("Offer made.")
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("order/{id}")
    public ResponseEntity<PagedModel<OfferDTO>> getByOrder(Pageable pageable, @PathVariable long id)
    {
        return new ResponseEntity<>(offerService.findByOrder(pageable, id), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<String> updateOffer(@RequestBody OfferDTO dto)
    {
        return offerService.updateOffer(dto.getId(), dto.getPrice(), dto.getDateOfDelivery()) ?
                ResponseEntity.ok("Updated")
                : ResponseEntity.badRequest().build();
    }
}