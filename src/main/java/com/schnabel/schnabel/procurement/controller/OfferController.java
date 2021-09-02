package com.schnabel.schnabel.procurement.controller;

import com.schnabel.schnabel.procurement.dto.*;

import java.util.Optional;

import com.schnabel.schnabel.procurement.dto.OfferCreationDTO;
import com.schnabel.schnabel.procurement.dto.OfferDTO;
import com.schnabel.schnabel.procurement.model.Offer;
import com.schnabel.schnabel.procurement.model.OfferStatus;
import com.schnabel.schnabel.procurement.service.IOfferService;
import com.schnabel.schnabel.security.util.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
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

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @GetMapping("supplier")
    public ResponseEntity<PagedModel<OfferDTO>> getBySupplier(Pageable pageable, @RequestHeader("Authorization") String authHeader)
    {
        String jws;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jws = authHeader.substring(7, authHeader.length());
        } else {
            return ResponseEntity.badRequest().build();
        }

        String email = jwtUtils.getEmailFromJws(jws);
        return new ResponseEntity<>(offerService.findBySupplier(pageable, email), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @GetMapping("supplier/filter/{f}")
    public ResponseEntity<PagedModel<OfferDTO>> getBySupplierFiltered(Pageable pageable, @PathVariable("f")OfferStatus status, OfferFilterDTO dto, @RequestHeader("Authorization") String authHeader) {
        String jws;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jws = authHeader.substring(7, authHeader.length());
        } else {
            return ResponseEntity.badRequest().build();
        }
        String email = jwtUtils.getEmailFromJws(jws);
        return ResponseEntity.ok(offerService.findBySupplierFiltered(pageable, email, status));
    }


    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @PostMapping("makeoffer")
    public ResponseEntity<String> createOffer(@RequestBody OfferCreationDTO creationDTO, @RequestHeader("Authorization") String authHeader)
    {
        String jws;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jws = authHeader.substring(7, authHeader.length());
        } else {
            return ResponseEntity.badRequest().build();
        }

        String email = jwtUtils.getEmailFromJws(jws);
        return offerService.createOffer(creationDTO.getPrice(), creationDTO.getDateOfDelivery(), creationDTO.getOrderId(), email) ?
                ResponseEntity.ok("Offer made.")
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("order/{id}")
    public ResponseEntity<PagedModel<OfferDTO>> getByOrder(Pageable pageable, @RequestHeader("Authorization") String authHeader, @PathVariable long id)
    {
        return new ResponseEntity<>(offerService.findByOrder(pageable, id), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<String> updateOffer(@RequestBody OfferDTO dto, @RequestHeader("Authorization") String authHeader)
    {
        return offerService.updateOffer(dto.getId(), dto.getPrice(), dto.getDateOfDelivery()) ?
                ResponseEntity.ok("Updated")
                : ResponseEntity.badRequest().build();
    }

    @PostMapping("acceptoffer")
    public ResponseEntity<String> acceptOffer(@RequestBody long offerId, @RequestHeader("Authorization") String authHeader, Pageable pageable) {
        String jws = jwtUtils.parseJwtFromAuthorizationHeader(authHeader);
        return offerService.acceptOffer(offerId, jwtUtils.getEmailFromJws(jws), pageable) ?
            ResponseEntity.ok("Accepted")
            : ResponseEntity.badRequest().build();
    }

    @GetMapping("editable/{id}")
    public ResponseEntity<String> editable(@PathVariable("id") Long id) {
        return offerService.isEditable(id) ?
                ResponseEntity.ok("Editable")
                : ResponseEntity.badRequest().build();
    }
}