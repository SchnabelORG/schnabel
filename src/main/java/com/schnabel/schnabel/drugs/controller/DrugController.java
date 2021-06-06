package com.schnabel.schnabel.drugs.controller;

import java.util.List;
import java.util.Map;

import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.drugs.service.IDrugService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("api/drug")
public class DrugController 
{
    private final IDrugService service;

    @Autowired
    public DrugController(IDrugService drugService) {
        this.service = drugService;
    }

    @Transactional
    @GetMapping("{id}")
    public ResponseEntity<DrugDTO> get(@PathVariable("id") Long id) {
        return service.findByIdDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<PagedModel<DrugDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(service.findAllDTO(pageable), HttpStatus.OK);
    }
    @GetMapping("search")
    public ResponseEntity<PagedModel<DrugDTO>> search(@RequestParam Map<String, String> params, Pageable pageable) {
        return new ResponseEntity<>(service.filteredSearch(params, pageable), HttpStatus.OK);
    }

    @GetMapping("allergy")
    public ResponseEntity<List<DrugDTO>> getAll() {
        return ResponseEntity.ok(service.findAllDTO());
    }
}
