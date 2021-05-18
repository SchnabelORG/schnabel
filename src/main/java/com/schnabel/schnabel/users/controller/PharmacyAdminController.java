package com.schnabel.schnabel.users.controller;

import java.util.Optional;

import com.schnabel.schnabel.users.dto.PatientDTO;
import com.schnabel.schnabel.users.dto.PharmacyAdminDTO;
import com.schnabel.schnabel.users.dto.PharmacyAdminDTOAssembler;
import com.schnabel.schnabel.users.dto.PharmacyAssigmentDTO;
import com.schnabel.schnabel.users.model.Patient;
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
    private final PharmacyAdminDTOAssembler pharmacyAdminDTOAssembler;
    private final PagedResourcesAssembler<PharmacyAdmin> pharmacyAdminDtoPagedResourcesAssembler;

    @Autowired
    public PharmacyAdminController(IPharmacyAdminService pharmacyAdminService, PharmacyAdminDTOAssembler pharmacyAdminDTOAssembler, PagedResourcesAssembler<PharmacyAdmin> pharmacyAdminDtoPagedResourcesAssembler) {
        this.pharmacyAdminService = pharmacyAdminService;
        this.pharmacyAdminDTOAssembler = pharmacyAdminDTOAssembler;
        this.pharmacyAdminDtoPagedResourcesAssembler = pharmacyAdminDtoPagedResourcesAssembler;
    }


    /**
     * Get pharmacyAdmin by id
     * @return PharmacyAdmin
     */
    @GetMapping("{id}")
    public ResponseEntity<PharmacyAdminDTO> get(@PathVariable long id)
    {
//        Optional<PharmacyAdmin> pharmacyAdmin = pharmacyAdminService.get(id);
//        return pharmacyAdmin.isPresent() ?
//            ResponseEntity.ok(pharmacyAdmin.get())
//            : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return pharmacyAdminService.get(id).map(pharmacyAdminDTOAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    /**
     * Get all pharmacyAdmins
     * @return Iterable of PharmacyAdmins
     */
    @GetMapping()
    public ResponseEntity<PagedModel<PharmacyAdminDTO>> getAll(Pageable pageable)
    {
//        return ResponseEntity.ok(pharmacyAdminService.getAll());
        Page<PharmacyAdmin> pharmacyAdmins = pharmacyAdminService.getAll(pageable);
        PagedModel<PharmacyAdminDTO> collModel = pharmacyAdminDtoPagedResourcesAssembler.toModel(pharmacyAdmins, pharmacyAdminDTOAssembler);
        return new ResponseEntity<>(collModel, HttpStatus.OK);
    }

    /**
     * Get all pharmacyAdmins for pharmacyId
     * @return Iterable of PharmacyAdmins
     * */
    @GetMapping("bypharmacy/{id}")
    public ResponseEntity<PagedModel<PharmacyAdminDTO>> getByPharmacy(Pageable pageable, @PathVariable long id)
    {
        Page<PharmacyAdmin> pharmacyAdmins = pharmacyAdminService.findByPharmacy(pageable, id);
        PagedModel<PharmacyAdminDTO> collModel = pharmacyAdminDtoPagedResourcesAssembler.toModel(pharmacyAdmins, pharmacyAdminDTOAssembler);
        return new ResponseEntity<>(collModel, HttpStatus.OK);
    }

    /**
     * Get all PharmacyAdmins without Pharmacy
     * @return Iterable of PharmacyAdmins
     */
    @GetMapping("nopharmacy")
    public ResponseEntity<PagedModel<PharmacyAdminDTO>> getNoPharmacy(Pageable pageable)
    {
        Page<PharmacyAdmin> pharmacyAdmins = pharmacyAdminService.findWithoutPharmacy(pageable);
        PagedModel<PharmacyAdminDTO> collModel = pharmacyAdminDtoPagedResourcesAssembler.toModel(pharmacyAdmins, pharmacyAdminDTOAssembler);
        return new ResponseEntity<>(collModel, HttpStatus.OK);
    }

    /**
     * Assigning Pharmacy to PharmacyAdmin
     * @param assigmentDTO
     * @return Updated PharmacyAdmin
     */
    @PostMapping("addpharmacy")
    public ResponseEntity<String> AssignPharmacy(@RequestBody PharmacyAssigmentDTO assigmentDTO)
    {
        return pharmacyAdminService.assignPharmacyAdmin(assigmentDTO.getPharmacyAdminId(), assigmentDTO.getPharmacyId()) ?
                ResponseEntity.ok("Registered")
                : ResponseEntity.badRequest().build();
    }
}
