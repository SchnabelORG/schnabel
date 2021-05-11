package com.schnabel.schnabel.users.controller;

import java.util.Optional;

import com.schnabel.schnabel.users.model.Supplier;
import com.schnabel.schnabel.users.service.ISupplierService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Supplier REST controller
 */
@RestController
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
    @GetMapping("/api/supplier/{id}")
    public ResponseEntity<Supplier> get(@PathVariable long id)
    {
        Optional<Supplier> supplier = supplierService.get(id);
        return supplier.isPresent() ?
            ResponseEntity.ok(supplier.get())
            : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

     /**
     * Get all suppliers
     * @return Iterable of Supplier
     */
    @GetMapping("/api/supplier")
    public ResponseEntity<Iterable<Supplier>> getAll()
    {
        Iterable<Supplier> suppliers = supplierService.getAll();
        return ResponseEntity.ok(suppliers);
    }
}
