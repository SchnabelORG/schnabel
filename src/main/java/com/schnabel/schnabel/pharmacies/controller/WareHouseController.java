package com.schnabel.schnabel.pharmacies.controller;

import com.schnabel.schnabel.pharmacies.model.WareHouse;
import com.schnabel.schnabel.pharmacies.service.IWareHouseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WareHouse REST controller
 */
@RestController
public class WareHouseController 
{
    private final IWareHouseService wareHouseService;

    @Autowired
    public WareHouseController(IWareHouseService wareHouseService)
    {
        this.wareHouseService = wareHouseService;
    }

    /**
     * Get all warehouses
     * @return Iterable of WareHouse
     */
    @GetMapping("/api/warehouse")
    public ResponseEntity<Iterable<WareHouse>> getAll()
    {
        return ResponseEntity.ok(wareHouseService.getAll());
    }
}
