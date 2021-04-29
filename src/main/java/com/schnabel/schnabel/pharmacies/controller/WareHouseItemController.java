package com.schnabel.schnabel.pharmacies.controller;

import com.schnabel.schnabel.pharmacies.model.WareHouseItem;
import com.schnabel.schnabel.pharmacies.service.IWareHouseItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WareHouseItem REST controller
 */
@RestController
public class WareHouseItemController 
{
    private final IWareHouseItemService wareHouseItemService;

    @Autowired
    public WareHouseItemController(IWareHouseItemService wareHouseItemService)
    {
        this.wareHouseItemService = wareHouseItemService;
    }

    /**
     * Get all warehouseitems
     * @return Iterable of WareHouseItem
     */
    @GetMapping("/api/warehouseitem")
    public ResponseEntity<Iterable<WareHouseItem>> getAll()
    {
        return ResponseEntity.ok(wareHouseItemService.getAll());
    }    
}
