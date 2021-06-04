package com.schnabel.schnabel.pharmacies.controller;

import java.util.List;

import com.schnabel.schnabel.drugs.dto.DrugPriceRequest;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemRequest;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemUpdateRequest;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;
import com.schnabel.schnabel.pharmacies.service.IWareHouseItemService;
import com.schnabel.schnabel.security.util.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/warehouseitem")
public class WareHouseItemController 
{
    private final IWareHouseItemService service;
    private final JwtUtils jwtUtils;

    @Autowired
    public WareHouseItemController(IWareHouseItemService service, JwtUtils jwtUtils) {
        this.service = service;
        this.jwtUtils = jwtUtils;
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
        return new ResponseEntity<>(service.findAllByPharmacyId(1l, pageable), HttpStatus.OK);
    }

    /**
     * Get stock of drug at specific pharmacy
     */
    @GetMapping("stock")
    public ResponseEntity<Integer> getStock(@RequestParam(name = "pharmacy_id", required = true) Long pharmacyId, @RequestParam(name = "drug_id", required = true) Long drugId) {
        return ResponseEntity.ok(service.getStock(pharmacyId, drugId));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteWareHouseItem(@PathVariable("id") Long id, @RequestHeader("Authorization") String authHeader) {
        return service.deleteWareHouseItem(id) ?
            ResponseEntity.ok("Deleted")
            : ResponseEntity.badRequest().build();

    }

    @PutMapping
    public ResponseEntity<String> updateWareHouseItem(@RequestBody WareHouseItemUpdateRequest wareHouseItemUpdateRequest, @RequestHeader("Authorization") String authHeader) {
        return service.updateWareHouseItem(wareHouseItemUpdateRequest) ?
            ResponseEntity.ok("Updated")
            : ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<String> addWareHouseItem(@RequestBody WareHouseItemRequest wareHouseItemRequest, @RequestHeader("Authorization") String authHeader) {
        return service.addWareHouseItem(wareHouseItemRequest) ?
            ResponseEntity.ok("Added")
            : ResponseEntity.badRequest().build();
    }

    @PostMapping("drugprice")
    public ResponseEntity<String> addDrugPrice(@RequestBody DrugPriceRequest drugPriceRequest, @RequestHeader("Authorization") String authHeader) {
        return service.addDrugPrice(drugPriceRequest) ?
            ResponseEntity.ok("Added")
            : ResponseEntity.badRequest().build();
    }

}
