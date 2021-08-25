package com.schnabel.schnabel.pharmacies.dto;

import com.schnabel.schnabel.drugs.controller.DrugController;
import com.schnabel.schnabel.drugs.controller.DrugPriceController;
import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.drugs.dto.DrugPriceDTO;
import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.model.DrugPrice;
import com.schnabel.schnabel.pharmacies.controller.PharmacyController;
import com.schnabel.schnabel.pharmacies.controller.WareHouseItemController;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Assembles WareHouseItem for JSON representation
 */
@Component
public class WareHouseItemDTOAssembler extends RepresentationModelAssemblerSupport<WareHouseItem, WareHouseItemDTO> {

    public WareHouseItemDTOAssembler() {
        super(WareHouseItemController.class, WareHouseItemDTO.class);
    }

    @Override
    public WareHouseItemDTO toModel(WareHouseItem entity) {
        WareHouseItemDTO dto = instantiateModel(entity);

        dto.add(linkTo(methodOn(WareHouseItemController.class).get(entity.getId())).withSelfRel());
        dto.setId(entity.getId());
        dto.setQuantity(entity.getQuantity());
        dto.setAvailable(entity.getAvailable());
        DrugPrice drugPrice = entity.getPriceForToday();  
        if (drugPrice == null) 
        {
            dto.setDrugPrice(DrugPriceDTO.builder()
            .id(-1l)
            .price(0)
            .build());
        } else 
        {      
        dto.setDrugPrice(DrugPriceDTO.builder()
            .id(drugPrice.getId())
            .price(drugPrice.getPrice())
            .build()
            .add(linkTo(methodOn(DrugPriceController.class).get(drugPrice.getId())).withSelfRel()));
        }
        Drug drug = entity.getDrug();
        dto.setDrug(DrugDTO.builder()
            .id(drug.getId())
            .name(drug.getName())
            .description(drug.getDescription())
            .build()
            .add(linkTo(methodOn(DrugController.class).get(drug.getId())).withSelfRel()));
        Pharmacy pharmacy = entity.getPharmacy();
        dto.setPharmacy(PharmacyDTO.builder()
            .id(pharmacy.getId())
            .build()
            .add(linkTo(methodOn(PharmacyController.class).getById(pharmacy.getId())).withSelfRel()));
        return dto;
    }
}
