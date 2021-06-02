package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.dto.PharmacyAdminDTO;
import com.schnabel.schnabel.users.dto.PharmacyAdminDTOAssembler;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.service.IPharmacyAdminService;

import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * PharmacyAdmin REST controller
 */
@RestController
@RequestMapping("api/pharmacyadmin")
public class PharmacyAdminController 
{
    private final IPharmacyAdminService pharmacyAdminService;
    private final PharmacyAdminDTOAssembler pharmacyAdminDTOAsm;
    private final PagedResourcesAssembler<PharmacyAdmin> pharmacyAdminPageAsm;
    private final JwtUtils jwtUtils;

    @Autowired
    public PharmacyAdminController(IPharmacyAdminService pharmacyAdminService, PharmacyAdminDTOAssembler pharmacyAdminDTOAsm, PagedResourcesAssembler<PharmacyAdmin> pharmacyAdminPageAsm, JwtUtils jwtUtils)
    {
        this.pharmacyAdminService = pharmacyAdminService;
        this.pharmacyAdminDTOAsm = pharmacyAdminDTOAsm;
        this.pharmacyAdminPageAsm = pharmacyAdminPageAsm;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Get pharmacy admin by id
     * @return PharmacyAdmin
     */
    @GetMapping("{id}")
    public ResponseEntity<PharmacyAdminDTO> get(@PathVariable long id)
    {
        return pharmacyAdminService.get(id).map(pharmacyAdminDTOAsm::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get Pharmacy admin by JWT
     * @param authHeader - Authorization header containing jwt
     * @return PharmacyAdminDTO if exists, else BadRequest
     */
    @GetMapping
    public ResponseEntity<PharmacyAdminDTO> getByJws(@RequestHeader("Authorization") String authHeader) {
        String jws = jwtUtils.parseJwtFromAuthorizationHeader(authHeader);
        String email = jwtUtils.getEmailFromJws(jws);
        return pharmacyAdminService.findByEmail(email)
            .map(pharmacyAdminDTOAsm::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("dermatologist")
    public ResponseEntity<PagedModel<DermatologistDTO>> getDermatologistsPharmacyAdmin(@RequestHeader("Authorization") String authHeader, Pageable pageable)
    {
        String jws = jwtUtils.parseJwtFromAuthorizationHeader(authHeader);
        return new ResponseEntity<>(pharmacyAdminService.getAllDermatologists(jwtUtils.getEmailFromJws(jws), pageable), HttpStatus.OK);
    } 

    @GetMapping("pharmacist")
    public ResponseEntity<PagedModel<PharmacistDTO>> getPharmacistsPharmacyAdmin(@RequestHeader("Authorization") String authHeader, Pageable pageable)
    {
        String jws = jwtUtils.parseJwtFromAuthorizationHeader(authHeader);
        return new ResponseEntity<>(pharmacyAdminService.getAllPharmacists(jwtUtils.getEmailFromJws(jws), pageable), HttpStatus.OK);
    } 

    @GetMapping("drug")
    public ResponseEntity<PagedModel<WareHouseItemDTO>> getDrugsPharmacyAdmin(@RequestHeader("Authorization") String authHeader, Pageable pageable)
    {
        String jws = jwtUtils.parseJwtFromAuthorizationHeader(authHeader);
        return new ResponseEntity<>(pharmacyAdminService.getAllDrugs(jwtUtils.getEmailFromJws(jws), pageable), HttpStatus.OK);
    } 
}
