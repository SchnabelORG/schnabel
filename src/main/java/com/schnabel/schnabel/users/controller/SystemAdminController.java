package com.schnabel.schnabel.users.controller;

import java.util.Optional;

import com.schnabel.schnabel.users.model.SystemAdmin;
import com.schnabel.schnabel.users.service.ISystemAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * SystemAdmin REST controller
 */
@RestController
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
    @GetMapping("/api/systemAdmin/{id}")
    public ResponseEntity<SystemAdmin> get(@PathVariable long id)
    {
        Optional<SystemAdmin> systemAdmin = systemAdminService.get(id);
        return systemAdmin.isPresent() ?
            ResponseEntity.ok(systemAdmin.get())
            : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Get all systemAdmins
     * @return Iterable of SystemAdmins
     */
    @GetMapping("/api/systemAdmin")
    public ResponseEntity<Iterable<SystemAdmin>> getAll()
    {
        return ResponseEntity.ok(systemAdminService.getAll());
    }
}
