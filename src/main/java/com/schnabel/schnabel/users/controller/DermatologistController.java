package com.schnabel.schnabel.users.controller;


import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.dto.DermatologistDTOAssembler;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.model.Pharmacist;

import java.util.Map;

import com.schnabel.schnabel.users.dto.DermatologistDTO;

import com.schnabel.schnabel.users.service.IDermatologistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Dermatologist REST controller
 */
@RestController
@RequestMapping("api/dermatologist")
public class DermatologistController 
{
    private final IDermatologistService dermatologistService;
    private final DermatologistDTOAssembler dermatologistDTOAssembler;
    private final PagedResourcesAssembler<Dermatologist> dermatologistDTOAsm;
    private final JwtUtils jwtUtils;

    @Autowired
    public DermatologistController(IDermatologistService dermatologistService, DermatologistDTOAssembler dermatologistDTOAssembler, PagedResourcesAssembler<Dermatologist> dermatologistDTOAsm, JwtUtils jwtUtils)
    {
        this.dermatologistService = dermatologistService;
        this.dermatologistDTOAssembler = dermatologistDTOAssembler;
        this.dermatologistDTOAsm = dermatologistDTOAsm;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("jwt")
    public ResponseEntity<DermatologistDTO> getByJws(@RequestHeader("Authorization") String authHeader) {
        String jws;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jws = authHeader.substring(7, authHeader.length());
        } else {
            return ResponseEntity.badRequest().build();
        }

        String email = jwtUtils.getEmailFromJws(jws);
        return dermatologistService.findByEmail(email)
                .map(dermatologistDTOAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
    /**
     * Get all dermatologists
     * @return Page of Dermatologist
     */
    @GetMapping
    public ResponseEntity<PagedModel<DermatologistDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(dermatologistService.getAllDTO(pageable), HttpStatus.OK);
    }

    /**
     * Get dermatologist by id
     * @return Dermatologist
     */
    @GetMapping("{id}")
    public ResponseEntity<DermatologistDTO> get(@PathVariable long id)
    {
        return dermatologistService.getDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get dermatologists by pahrmacy id
     * @return Dermatologist
     */
    @GetMapping("pharmacy/{id}")
    public ResponseEntity<PagedModel<DermatologistDTO>> getAllByPharmacyId(@PathVariable("id") Long pharmacyId, Pageable pageable) 
    {
        return new ResponseEntity<>(dermatologistService.findAllByPharmacy(pharmacyId, pageable), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<DermatologistDTO> put(@RequestHeader("Authorization") String authHeader, @RequestBody DermatologistDTO dermatologistDTO)
    {
        Optional<Dermatologist> dermatologist = dermatologistService.get(dermatologistDTO.getId());
        if(dermatologist.isPresent()){
            dermatologist.get().setAddress(dermatologistDTO.getAddress());
            dermatologist.get().setName(dermatologistDTO.getName());
            dermatologist.get().setSurname(dermatologistDTO.getSurname());
        }
        dermatologistService.update(dermatologist.get());
        return dermatologistService.get(dermatologist.get().getId()).map(dermatologistDTOAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("search")
    public ResponseEntity<PagedModel<DermatologistDTO>> filteredSearch(@RequestParam Map<String, String> params, Pageable pageable) {
        return new ResponseEntity<>(dermatologistService.filteredSearch(params, pageable), HttpStatus.OK);
    }

}
