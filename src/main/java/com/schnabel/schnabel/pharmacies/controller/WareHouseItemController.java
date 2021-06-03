package com.schnabel.schnabel.pharmacies.controller;

import com.schnabel.schnabel.pharmacies.dto.DoesHaveDrugDTO;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;
import com.schnabel.schnabel.pharmacies.service.IWareHouseItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    /**
     * Get warehouseitems by pharmacy id
     * @return WareHouseItemDTO
     */
    @GetMapping("pharmacy/{id}")
    public ResponseEntity<PagedModel<WareHouseItemDTO>> getAllByPharmacyId(@PathVariable("id") Long pharmacyId, Pageable pageable) 
    {
        return new ResponseEntity<>(wareHouseItemService.findAllByPharmacyId(pharmacyId, pageable), HttpStatus.OK);
    }


    @PostMapping("doeshave")
    public ResponseEntity<Boolean> getAllByPharmacyId(@RequestBody DoesHaveDrugDTO drugDTO)
    {

        Optional<WareHouseItem> wareHouseItem = wareHouseItemService.findWareHouseItemByPharmacyAndDrugId(drugDTO.getDrugId(), drugDTO.getPharmacyId());
        if(wareHouseItem.isPresent() && wareHouseItem.get().getAvailable() >= drugDTO.getQuantity()){
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        }
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
    }
}
