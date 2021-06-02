package com.schnabel.schnabel.drugs.dto;

import com.schnabel.schnabel.drugs.controller.DrugController;
import com.schnabel.schnabel.drugs.controller.DrugPriceController;
import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.model.DrugPrice;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DrugPriceDTOAssembler extends RepresentationModelAssemblerSupport<DrugPrice, DrugPriceDTO> 
{
    public DrugPriceDTOAssembler() {
        super(DrugPriceController.class, DrugPriceDTO.class);
    }

    @Override
    public DrugPriceDTO toModel(DrugPrice entity) {
        DrugPriceDTO dto = instantiateModel(entity);

        dto.add(linkTo(methodOn(DrugPriceController.class).get(entity.getId())).withSelfRel());
        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        Drug drug = entity.getDrug();
        dto.setDrug(DrugDTO.builder()
            .id(drug.getId())
            .build()
            .add(linkTo(methodOn(DrugController.class).get(drug.getId())).withSelfRel()));

        return dto;
    }
}
