package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.users.dto.PharmacyAdminDTO;
import com.schnabel.schnabel.users.dto.PharmacyAdminDTOAssembler;
import com.schnabel.schnabel.users.dto.PharmacyAssignmentDTO;
import com.schnabel.schnabel.users.dto.RegisterPharmacyAdminRequest;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.service.IPharmacyAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * PharmacyAdmin REST controller
 */
@RestController
@RequestMapping("api/pharmacyadmin")
public class PharmacyAdminController 
{
    private final IPharmacyAdminService pharmacyAdminService;

    @Autowired
    public PharmacyAdminController(IPharmacyAdminService pharmacyAdminService, PharmacyAdminDTOAssembler pharmacyAdminDTOAssembler, PagedResourcesAssembler<PharmacyAdmin> pharmacyAdminDtoPagedResourcesAssembler) {
        this.pharmacyAdminService = pharmacyAdminService;
    }


    /**
     * Get pharmacyAdmin by id
     * @return PharmacyAdmin
     */
    @GetMapping("{id}")
    public ResponseEntity<PharmacyAdminDTO> get(@PathVariable long id)
    {
        return pharmacyAdminService.getDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all pharmacyAdmins
     * @return Iterable of PharmacyAdmins
     */
    @GetMapping()
    public ResponseEntity<PagedModel<PharmacyAdminDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(pharmacyAdminService.getAllDTO(pageable), HttpStatus.OK);
    }

    /**
     * Get all pharmacyAdmins for pharmacyId
     * @return Iterable of PharmacyAdmins
     * */
    @GetMapping("bypharmacy/{id}")
    public ResponseEntity<PagedModel<PharmacyAdminDTO>> getByPharmacy(Pageable pageable, @PathVariable long id)
    {
        return new ResponseEntity<>(pharmacyAdminService.findByPharmacy(pageable, id), HttpStatus.OK);
    }

    /**
     * Get all PharmacyAdmins without Pharmacy
     * @return Iterable of PharmacyAdmins
     */
    @GetMapping("nopharmacy")
    public ResponseEntity<PagedModel<PharmacyAdminDTO>> getNoPharmacy(Pageable pageable)
    {
        return new ResponseEntity<>(pharmacyAdminService.findWithoutPharmacy(pageable), HttpStatus.OK);
    }

    /**
     * Assigning Pharmacy to PharmacyAdmin
     * @param assignmentDTO
     * @return Updated PharmacyAdmin
     */
    @PostMapping("addpharmacy")
    public ResponseEntity<String> AssignPharmacy(@RequestBody PharmacyAssignmentDTO assignmentDTO)
    {
        return pharmacyAdminService.assignPharmacyAdmin(assignmentDTO.getPharmacyAdminId(), assignmentDTO.getPharmacyId()) ?
                ResponseEntity.ok("Pharmacy admin assigned")
                : ResponseEntity.badRequest().build();
    }

    @PostMapping("register")
    public ResponseEntity<String> registerPharmacyAdmin(@RequestBody RegisterPharmacyAdminRequest request)
    {
        return pharmacyAdminService.registerPharmacyAdmin(request.getName(), request.getSurname(), request.getEmail(), request.getPassword(), request.getAddress()) ?
                ResponseEntity.ok("Registered")
                : ResponseEntity.badRequest().build();

    }
}
