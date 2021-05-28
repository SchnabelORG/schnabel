package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.dto.PharmacistDTOAssembler;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.service.IPharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Pharmacist REST controller
 */
@RestController
@RequestMapping("api/pharmacist")
public class PharmacistController
{
    private final IPharmacistService pharmacistService;
    private final JwtUtils jwtUtils;
    private final PharmacistDTOAssembler pharmacistDTOAssembler;
    private final PagedResourcesAssembler<Pharmacist> pharmacistPagedResourcesAssembler;

    @Autowired
    public PharmacistController(IPharmacistService pharmacistService, JwtUtils jwtUtils, PharmacistDTOAssembler pharmacistDTOAssembler, PagedResourcesAssembler<Pharmacist> pharmacistPagedResourcesAssembler)
    {
        this.pharmacistService = pharmacistService;
        this.jwtUtils = jwtUtils;
        this.pharmacistDTOAssembler = pharmacistDTOAssembler;
        this.pharmacistPagedResourcesAssembler = pharmacistPagedResourcesAssembler;
    }

    /**
     * Get all pharmacists
     * @return Iterable of Pharmacist
     */
    @GetMapping
    public ResponseEntity<PharmacistDTO> getByJws(@RequestHeader("Authorization") String authHeader) {
        String jws;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jws = authHeader.substring(7, authHeader.length());
        } else {
            return ResponseEntity.badRequest().build();
        }

        String email = jwtUtils.getEmailFromJws(jws);
        return pharmacistService.findByEmail(email)
                .map(pharmacistDTOAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Get pharmacists by id
     * @return Pharmacist
     */
    @GetMapping("{id}")
    public ResponseEntity<PharmacistDTO> get(@PathVariable Long id)
    {
        return pharmacistService.getDTO(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("pharmacy/{id}")
    public ResponseEntity<PagedModel<PharmacistDTO>> getByPharmacyId(@PathVariable("id") Long pharmacyId, Pageable pageable) {
        Page<Pharmacist> pharmacists = pharmacistService.findByPharmacy(pharmacyId, pageable);
        PagedModel<PharmacistDTO> pagedModel = pharmacistPagedResourcesAssembler.toModel(pharmacists, pharmacistDTOAssembler);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<PharmacistDTO> put(@RequestHeader("Authorization") String authHeader, @RequestBody PharmacistDTO pharmacistDTO)
    {
        Optional<Pharmacist> pharmacist = pharmacistService.get(pharmacistDTO.getId());
        if(pharmacist.isPresent()){
            pharmacist.get().setAddress(pharmacistDTO.getAddress());
            pharmacist.get().setName(pharmacistDTO.getName());
            pharmacist.get().setSurname(pharmacistDTO.getSurname());
        }
        pharmacistService.update(pharmacist.get());
        return pharmacistService.get(pharmacist.get().getId()).map(pharmacistDTOAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}