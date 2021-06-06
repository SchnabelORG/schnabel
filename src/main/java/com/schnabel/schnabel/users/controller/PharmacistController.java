package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.auth.dto.LoginRequest;
import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.dto.FreePharmacistLookupRequest;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.dto.PharmacistDTOAssembler;
import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.service.IPharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

/**
 * Pharmacist REST controller
 */
@RestController
@RequestMapping("api/pharmacist")
public class PharmacistController
{
    private final IPharmacistService service;
    private final JwtUtils jwtUtils;
    private final PharmacistDTOAssembler pharmacistDTOAssembler;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PharmacistController(IPharmacistService service, JwtUtils jwtUtils, PharmacistDTOAssembler pharmacistDTOAssembler, PagedResourcesAssembler<Pharmacist> pharmacistPagedResourcesAssembler)
    {
        this.service = service;
        this.jwtUtils = jwtUtils;
        this.pharmacistDTOAssembler = pharmacistDTOAssembler;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Get all pharmacists
     * @return Iterable of Pharmacist
     */
    @GetMapping
    public ResponseEntity<PagedModel<PharmacistDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(service.findAllDTO(pageable), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("jwt")
    public ResponseEntity<PharmacistDTO> getByJws(@RequestHeader("Authorization") String authHeader) {
        String jws;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jws = authHeader.substring(7, authHeader.length());
        } else {
            return ResponseEntity.badRequest().build();
        }

        String email = jwtUtils.getEmailFromJws(jws);
        return service.findByEmail(email)
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
        return service.findbyIdDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get pharmacists with free appointments via pharmacy id
     */
    @PostMapping("free_pharmacy_appt")
    public ResponseEntity<PagedModel<PharmacistDTO>> getFreeByPharmacyId(@RequestBody FreePharmacistLookupRequest req, Pageable pageable) {
        return new ResponseEntity<>(service.findFreeByPharmacy(req, pageable), HttpStatus.OK);
    }

    @GetMapping("pharmacy/{id}")
    public ResponseEntity<PagedModel<PharmacistDTO>> getByPharmacyId(@PathVariable("id") Long pharmacyId, Pageable pageable) {
        return new ResponseEntity<>(service.findByPharmacy(pharmacyId, pageable), HttpStatus.OK);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<PharmacistDTO> put(@RequestHeader("Authorization") String authHeader, @RequestBody PharmacistDTO pharmacistDTO)
    {
        Optional<Pharmacist> pharmacist = service.get(pharmacistDTO.getId());
        if(pharmacist.isPresent()){
            pharmacist.get().setAddress(pharmacistDTO.getAddress());
            pharmacist.get().setName(pharmacistDTO.getName());
            pharmacist.get().setSurname(pharmacistDTO.getSurname());
        }
        service.update(pharmacist.get());
        return service.get(pharmacist.get().getId()).map(pharmacistDTOAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @Transactional
    @PutMapping("pass")
    public ResponseEntity<PharmacistDTO> changePassword(@RequestHeader("Authorization") String authHeader, @RequestBody LoginRequest dto)
    {
        Optional<Pharmacist> pharmacist = service.findByEmail(dto.getEmail());
        if(pharmacist.isPresent()){
            pharmacist.get().setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        service.update(pharmacist.get());
        return service.get(pharmacist.get().getId()).map(pharmacistDTOAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("search")
    public ResponseEntity<PagedModel<PharmacistDTO>> filteredSearch(@RequestParam Map<String, String> params, Pageable pageable) {
        return new ResponseEntity<>(service.filteredSearch(params, pageable), HttpStatus.OK);
    }
}