package com.schnabel.schnabel.users.controller;

import java.util.Optional;

import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.PharmacyAdminDTO;
import com.schnabel.schnabel.users.dto.PharmacyAdminDTOAssembler;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.service.IPharmacyAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.StringUtils;

/**
 * PharmacyAdmin REST controller
 */
@RestController
@RequestMapping("api/pharmacyadmin")
public class PharmacyAdminController 
{
    private final IPharmacyAdminService pharmacyAdminService;
    private final PharmacyAdminDTOAssembler pharmacyAdminDTOAssembler;
    private final PagedResourcesAssembler<PharmacyAdmin> pharmacistPageAsm;
    private final JwtUtils jwtUtils;

    @Autowired
    public PharmacyAdminController(IPharmacyAdminService pharmacyAdminService, PharmacyAdminDTOAssembler pharmacyAdminDTOAssembler, PagedResourcesAssembler<PharmacyAdmin> pharmacistPageAsm, JwtUtils jwtUtils)
    {
        this.pharmacyAdminService = pharmacyAdminService;
        this.pharmacyAdminDTOAssembler = pharmacyAdminDTOAssembler;
        this.pharmacistPageAsm = pharmacistPageAsm;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Get all pharmacyAdmins
     * @return Iterable of PharmacyAdmin
     */
    @GetMapping
    public ResponseEntity<PagedModel<PharmacyAdminDTO>> getAll(Pageable pageable)
    {
        Page<PharmacyAdmin> pharmacyAdmins = pharmacyAdminService.getAll(pageable);
        PagedModel<PharmacyAdminDTO> pagedModel = pharmacistPageAsm.toModel(pharmacyAdmins, pharmacyAdminDTOAssembler);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    /**
     * Get pharmacyAdmin by id
     * @return PharmacyAdmin
     */
    @GetMapping("{id}")
    public ResponseEntity<PharmacyAdminDTO> get(@PathVariable Long id)
    {
        return pharmacyAdminService.get(id).map(pharmacyAdminDTOAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get PharmacyAdmin by JWT
     * @param authHeader - Authorization header containing jwt
     * @return PharmacyAdminDTO if exists, else BadRequest
     */
    @GetMapping("byjws")
    public ResponseEntity<PharmacyAdminDTO> getByJws(@RequestHeader("Authorization") String authHeader) {
        String jws;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jws = authHeader.substring(7, authHeader.length());
        } else {
            return ResponseEntity.badRequest().build();
        }

        String email = jwtUtils.getEmailFromJws(jws);
        return pharmacyAdminService.findByEmail(email)
            .map(pharmacyAdminDTOAssembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.badRequest().build());
    }
}
