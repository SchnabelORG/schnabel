package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.users.dto.SupplierDTO;
import com.schnabel.schnabel.users.service.ISupplierService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Supplier REST controller
 */
@RestController
@RequestMapping("api/supplier")
public class SupplierController 
{
    private final ISupplierService supplierService;

    @Autowired
    public SupplierController(ISupplierService supplierService)
    {
        this.supplierService = supplierService;
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
    @GetMapping
    public ResponseEntity<PagedModel<SupplierDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(supplierService.getAllDTO(pageable), HttpStatus.OK);
    }
}
