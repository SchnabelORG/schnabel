package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.users.dto.RegisterPharmacyEmployeeRequest;
import com.schnabel.schnabel.users.dto.SystemAdminDTO;
import com.schnabel.schnabel.users.service.ISystemAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * SystemAdmin REST controller
 */
@RestController
@RequestMapping("api/systemAdmin")
public class SystemAdminController 
{
    private final ISystemAdminService systemAdminService;

    @Autowired
    public SystemAdminController(ISystemAdminService systemAdminService)
    {
        this.systemAdminService = systemAdminService;
    }

    /**
     * Get systemAdmin by id
     * @return SystemAdmin
     */
    @GetMapping("{id}")
    public ResponseEntity<SystemAdminDTO> get(@PathVariable long id)
    {
        return systemAdminService.getDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all systemAdmins
     * @return Iterable of SystemAdmins
     */
    @GetMapping()
    public ResponseEntity<PagedModel<SystemAdminDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(systemAdminService.getAllDTO(pageable), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> registerPharmacyAdmin(@RequestBody RegisterPharmacyEmployeeRequest request)
    {
        return systemAdminService.registerSystemAdmin(request.getName(), request.getSurname(), request.getEmail(), request.getPassword(), request.getAddress()) ?
                ResponseEntity.ok("Registered")
                : ResponseEntity.badRequest().build();
    }
}
