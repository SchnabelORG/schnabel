package com.schnabel.schnabel.drugs.controller;

import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.drugs.service.IDrugService;

import com.schnabel.schnabel.users.dto.PharmacistDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/drug")
public class DrugController 
{
    private final IDrugService drugService;

    @Autowired
    public DrugController(IDrugService drugService) {
        this.drugService = drugService;
    }

    @GetMapping("{id}")
    public ResponseEntity<DrugDTO> get(@PathVariable("id") Long id) {
        return drugService.findByIdDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<PagedModel<DrugDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(drugService.findAllDTO(pageable), HttpStatus.OK);
    }
}
