package com.schnabel.schnabel.procurement.controller;

import java.util.Optional;

import com.schnabel.schnabel.procurement.dto.OfferCreationDTO;
import com.schnabel.schnabel.procurement.dto.OfferDTO;
import com.schnabel.schnabel.procurement.model.Offer;
import com.schnabel.schnabel.procurement.service.IOfferService;
import com.schnabel.schnabel.procurement.service.IOrderService;
import com.schnabel.schnabel.security.util.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Offer REST controller
 */
@RestController
@RequestMapping("api/offer")
public class OfferController
{
    private final IOfferService offerService;
    private final JwtUtils jwtUtils;

    @Autowired
    public OfferController(IOfferService offerService, JwtUtils jwtUtils)
    {
        this.offerService = offerService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Get all offers
     * @return Iterable of Offer
     */
    @GetMapping()
    public ResponseEntity<PagedModel<OfferDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(offerService.getAllDTO(pageable), HttpStatus.OK);
    }

    /**
     * Get offer by id
     * @return Offer
     */
    @GetMapping("{id}")
    public ResponseEntity<OfferDTO> get(@PathVariable long id)
    {
        return offerService.getDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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

    @PostMapping("acceptoffer")
    public ResponseEntity<String> acceptOffer(@RequestBody OfferDTO dto, @RequestHeader("Authorization") String authHeader) {
        String jws = jwtUtils.parseJwtFromAuthorizationHeader(authHeader);
        return offerService.acceptOffer(jwtUtils.getEmailFromJws(jws)) ?
            ResponseEntity.ok("Accepted")
            : ResponseEntity.badRequest().build();
    }
}