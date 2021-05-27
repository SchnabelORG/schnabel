package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.users.dto.*;
import com.schnabel.schnabel.users.model.Supplier;
import com.schnabel.schnabel.users.service.ISupplierService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

/**
 * Supplier REST controller
 */
@RestController
@RequestMapping("api/supplier")
public class SupplierController 
{
    private final ISupplierService supplierService;
    private final SupplierDTOAssembler supplierDTOAssembler;
    private final PagedResourcesAssembler<Supplier> pagedResourcesAssembler;


    @Autowired
    public SupplierController(ISupplierService supplierService, SupplierDTOAssembler supplierDTOAssembler, PagedResourcesAssembler<Supplier> pagedResourcesAssembler)
    {
        this.supplierService = supplierService;
        this.supplierDTOAssembler = supplierDTOAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

     /**
     * Get supplier by id
     * @return Supplier
     */
    @GetMapping("{id}")
    public ResponseEntity<SupplierDTO> get(@PathVariable long id)
    {
        return supplierService.get(id).map(supplierDTOAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

     /**
     * Get all suppliers
     * @return Iterable of Supplier
     */
    @GetMapping()
    public ResponseEntity<PagedModel<SupplierDTO>> getAll(Pageable pageable)
    {
//        Iterable<Supplier> suppliers = supplierService.getAll();
//        return ResponseEntity.ok(suppliers);
        Page<Supplier> suppliers = supplierService.getAll(pageable);
        PagedModel<SupplierDTO> collModel = pagedResourcesAssembler.toModel(suppliers, supplierDTOAssembler);
        return new ResponseEntity<>(collModel, HttpStatus.OK);

    }

    /**
     * Register new Supplier
     * @param req - Supplier registration request containing required info
     * @return OK if registered, else BadRequest
     */
    @PostMapping
    public ResponseEntity<String> registerSupplier(@RequestBody RegisterSupplierRequest req)
    {
        return supplierService.registerSupplier(req.getName(), req.getSurname(), req.getEmail(), req.getPassword(), req.getAddress(), req.getCompany()) ?
                ResponseEntity.ok("Registered")
                : ResponseEntity.badRequest().build();
    }
}
