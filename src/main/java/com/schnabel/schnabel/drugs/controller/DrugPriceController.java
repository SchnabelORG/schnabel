package com.schnabel.schnabel.drugs.controller;

import com.schnabel.schnabel.drugs.dto.DrugPriceDTO;
import com.schnabel.schnabel.drugs.service.IDrugPriceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("api/drugprice")
public class DrugPriceController 
{
    private final IDrugPriceService drugPriceService;

    @Autowired
    public DrugPriceController(IDrugPriceService drugPriceService) {
        this.drugPriceService = drugPriceService;
    }

    @Transactional
    @GetMapping("{id}")
    public ResponseEntity<DrugPriceDTO> get(@PathVariable("id") Long id) {
        return drugPriceService.findByIdDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
