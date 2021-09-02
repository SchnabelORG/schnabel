package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.*;
import com.schnabel.schnabel.users.service.ISystemAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * SystemAdmin REST controller
 */
@RestController
@RequestMapping("api/systemAdmin")
public class SystemAdminController 
{
    private final ISystemAdminService systemAdminService;
    private final JwtUtils jwtUtils;

    @Autowired
    public SystemAdminController(ISystemAdminService systemAdminService, JwtUtils jwtUtils)
    {
        this.systemAdminService = systemAdminService;
        this.jwtUtils = jwtUtils;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("register")
    public ResponseEntity<String> regiserAdmin(@RequestBody RegisterPharmacyEmployeeRequest request)
    {
        return systemAdminService.registerSystemAdmin(request.getName(), request.getSurname(), request.getEmail(), request.getPassword(), request.getAddress()) ?
                ResponseEntity.ok("Registered")
                : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("active")
    public ResponseEntity<String> checkIsActive(@RequestHeader("Authorization") String authHeader ) {
        String jws;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jws = authHeader.substring(7, authHeader.length());
        } else {
            return ResponseEntity.badRequest().build();
        }
        String email = jwtUtils.getEmailFromJws(jws);
        return systemAdminService.isActive(email) ?
                ResponseEntity.ok("Active")
                : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("pass")
    public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String authHeader, @RequestBody PasswordChangeDTO dto) {
        String jws;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jws = authHeader.substring(7, authHeader.length());
        } else {
            return ResponseEntity.badRequest().build();
        }
        String email = jwtUtils.getEmailFromJws(jws);
        if(!dto.isValid()) {
            return ResponseEntity.badRequest().build();
        }
        return systemAdminService.changePassword(email, dto.getNewPassword()) ?
                ResponseEntity.ok("Password changed")
                : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("get")
    public ResponseEntity<?> getFromJWT(@RequestHeader("Authorization") String authHeader ) {
        String jws;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jws = authHeader.substring(7, authHeader.length());
        } else {
            return ResponseEntity.badRequest().build();
        }
        String email = jwtUtils.getEmailFromJws(jws);
        Optional<SystemAdminDTO> systemAdmin = systemAdminService.findByEmail(email);
        if(!systemAdmin.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(systemAdmin);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> registerSystemAdmin(@RequestBody RegisterRequest req) {
        return systemAdminService.registerSystemAdmin(req.getName(), req.getSurname(), req.getEmail(), req.getPassword(), req.getAddress()) ?
                ResponseEntity.ok("Registered")
                : ResponseEntity.badRequest().build();
    }
}
