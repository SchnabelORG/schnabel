package com.schnabel.schnabel.pharmacies.controller;

import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.pharmacies.service.IWareHouseItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/warehouseitem")
public class WareHouseItemController 
{
    private final IWareHouseItemService wareHouseItemService;

    @Autowired
    public WareHouseItemController(IWareHouseItemService wareHouseItemService) {
        this.wareHouseItemService = wareHouseItemService;
    }

    @GetMapping("{id}")
    public ResponseEntity<WareHouseItemDTO> get(@PathVariable("id") Long id) {
        return wareHouseItemService.findByIdDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
