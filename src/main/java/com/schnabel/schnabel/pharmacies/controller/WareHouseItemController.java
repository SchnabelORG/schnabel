package com.schnabel.schnabel.pharmacies.controller;

import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.pharmacies.service.IWareHouseItemService;

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

@RestController
@RequestMapping("api/warehouseitem")
public class WareHouseItemController 
{
    private final IWareHouseItemService service;

    @Autowired
    public WareHouseItemController(IWareHouseItemService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<WareHouseItemDTO> get(@PathVariable("id") Long id) {
        return service.findByIdDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get warehouseitems by pharmacy id
     * @return WareHouseItemDTO
     */
    @GetMapping("pharmacy/{id}")
    public ResponseEntity<PagedModel<WareHouseItemDTO>> getAllByPharmacyId(@PathVariable("id") Long pharmacyId, Pageable pageable) 
    {
        return new ResponseEntity<>(service.findAllByPharmacyId(pharmacyId, pageable), HttpStatus.OK);
    }

    /**
     * Get stock of drug at specific pharmacy
     */
    @GetMapping("stock")
    public ResponseEntity<Integer> getStock(@RequestParam(name = "pharmacy_id", required = true) Long pharmacyId, @RequestParam(name = "drug_id", required = true) Long drugId) {
        return ResponseEntity.ok(service.getStock(pharmacyId, drugId));
    }
}
