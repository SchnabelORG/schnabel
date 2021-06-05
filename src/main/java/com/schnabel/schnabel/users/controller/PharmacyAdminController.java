package com.schnabel.schnabel.users.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.dto.PharmacistRequest;
import com.schnabel.schnabel.users.dto.PharmacyAdminDTO;
import com.schnabel.schnabel.users.dto.PharmacyAdminDTOAssembler;
import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.service.IPharmacyAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final JwtUtils jwtUtils;

    @Autowired
    public PharmacyAdminController(IPharmacyAdminService pharmacyAdminService, PharmacyAdminDTOAssembler pharmacyAdminDTOAsm, JwtUtils jwtUtils)
    {
        this.pharmacyAdminService = pharmacyAdminService;
        this.pharmacyAdminDTOAsm = pharmacyAdminDTOAsm;
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

    @DeleteMapping("removepharmacist/{id}")
    public ResponseEntity<String> removePharmacist(@PathVariable("id") Long id, @RequestHeader("Authorization") String authHeader)
    {
        return pharmacyAdminService.removePharmacist(id) ?
            ResponseEntity.ok("Removed")
            : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("removedermatologist/{id}")
    public ResponseEntity<String> removeDermatologist(@PathVariable("id") Long id, @RequestHeader("Authorization") String authHeader)
    {
        String email = jwtUtils.getEmailFromJws(jwtUtils.parseJwtFromAuthorizationHeader(authHeader));
        return pharmacyAdminService.removeDermatologist(id, email) ?
            ResponseEntity.ok("Removed")
            : ResponseEntity.badRequest().build();
    }

    @GetMapping("adddermatologist")
    public ResponseEntity<List<Dermatologist>> getDermatologistsNotPharmacy(@RequestHeader("Authorization") String authHeader, Pageable pageable) 
    {
        Long id = pharmacyAdminService.findByEmail(jwtUtils.getEmailFromJws(jwtUtils.parseJwtFromAuthorizationHeader(authHeader))).get().getPharmacy().getId();
        return new ResponseEntity<>(pharmacyAdminService.getDermatologistNotPharmacy(id, pageable), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<PharmacyAdminDTO> put(@RequestHeader("Authorization") String authHeader, @RequestBody PharmacyAdminDTO pharmacyAdminDTO)
    {
        Optional<PharmacyAdmin> pharmacyAdmin = pharmacyAdminService.get(pharmacyAdminDTO.getId());
        if(pharmacyAdmin.isPresent()){
            pharmacyAdmin.get().setAddress(pharmacyAdminDTO.getAddress());
            pharmacyAdmin.get().setName(pharmacyAdminDTO.getName());
            pharmacyAdmin.get().setSurname(pharmacyAdminDTO.getSurname());
        }
        pharmacyAdminService.update(pharmacyAdmin.get());
        return pharmacyAdminService.get(pharmacyAdmin.get().getId()).map(pharmacyAdminDTOAsm::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("addpharmacist")
    public ResponseEntity<String> removeDermatologist(@RequestBody PharmacistRequest pharmacistRequest, @RequestHeader("Authorization") String authHeader) 
    {
        String email = jwtUtils.getEmailFromJws(jwtUtils.parseJwtFromAuthorizationHeader(authHeader));
        return pharmacyAdminService.addPharmacist(pharmacistRequest, email) ?
            ResponseEntity.ok("Added")
            : ResponseEntity.badRequest().build();
    }

    @GetMapping("searchdermatologist")
    public ResponseEntity<PagedModel<DermatologistDTO>> filteredSearchPharmacyAdmin(@RequestParam Map<String, String> params, Pageable pageable, @RequestHeader("Authorization") String authHeader) 
    {
        String email = jwtUtils.getEmailFromJws(jwtUtils.parseJwtFromAuthorizationHeader(authHeader));
        return new ResponseEntity<>(pharmacyAdminService.filteredSearchPharmacyAdmin(params, email, pageable), HttpStatus.OK);
    }

}
