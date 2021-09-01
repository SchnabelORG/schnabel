package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.*;
import com.schnabel.schnabel.users.service.ISupplierService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.schnabel.schnabel.users.dto.SupplierDTO;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

/**
 * Supplier REST controller
 */
@RestController
@RequestMapping("api/supplier")
public class SupplierController 
{
    private final ISupplierService supplierService;
    private final JwtUtils jwtUtils;

    @Autowired
    public SupplierController(ISupplierService supplierService, JwtUtils jwtUtils)
    {
        this.supplierService = supplierService;
        this.jwtUtils = jwtUtils;
    }

     /**
     * Get supplier by id
     * @return Supplier
     */
    @GetMapping("{id}")
    public ResponseEntity<SupplierDTO> get(@PathVariable long id)
    {
        return supplierService.getDTO(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

     /**
     * Get all suppliers
     * @return Page of Supplier
     */
    @GetMapping()
    public ResponseEntity<PagedModel<SupplierDTO>> getAll(Pageable pageable)
    {
//        Iterable<Supplier> suppliers = supplierService.getAll();
//        return ResponseEntity.ok(suppliers);
        return new ResponseEntity<>(supplierService.getAllDTO(pageable), HttpStatus.OK);
    }

    /**
     * Register new Supplier
     * @param req - Supplier registration request containing required info
     * @return OK if registered, else BadRequest
     */
    @PostMapping
    public ResponseEntity<String> registerSupplier(@RequestBody RegisterSupplierRequest req)
    {
        return supplierService.registerSupplier(req.getName(), req.getSurname(), req.getEmail(), req.getPassword(), req.getAddress(), req.getFirm()) ?
                ResponseEntity.ok("Registered")
                : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        return supplierService.remove(id) ?
                ResponseEntity.ok("Removed")
                : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @GetMapping("get")
    public ResponseEntity<?> getSupplierFromJWT(@RequestHeader("Authorization") String authHeader ) {
        String jws;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jws = authHeader.substring(7, authHeader.length());
        } else {
            return ResponseEntity.badRequest().build();
        }
        String email = jwtUtils.getEmailFromJws(jws);
        Optional<SupplierDTO> supplier = supplierService.findByEmail(email);
        if(!supplier.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(supplier);
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @GetMapping("active")
    public ResponseEntity<String> checkIsActive(@RequestHeader("Authorization") String authHeader ) {
        String jws;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jws = authHeader.substring(7, authHeader.length());
        } else {
            return ResponseEntity.badRequest().build();
        }
        String email = jwtUtils.getEmailFromJws(jws);
        return supplierService.isActive(email) ?
                ResponseEntity.ok("Active")
                : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
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
        return supplierService.changePassword(email, dto.getNewPassword()) ?
                ResponseEntity.ok("Password changed")
                : ResponseEntity.badRequest().build();
    }


    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @PutMapping
    public ResponseEntity<SupplierDTO> put(@RequestBody SupplierDTO dto, @RequestHeader("Authorization") String authHeader) {
        String jws;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jws = authHeader.substring(7, authHeader.length());
        } else {
            return ResponseEntity.badRequest().build();
        }
        String email = jwtUtils.getEmailFromJws(jws);

        return supplierService.updateSupplier(dto, email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

}
