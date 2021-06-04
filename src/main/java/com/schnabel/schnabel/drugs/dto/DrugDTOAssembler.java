package com.schnabel.schnabel.drugs.dto;

import com.schnabel.schnabel.drugs.controller.DrugController;
import com.schnabel.schnabel.drugs.model.Drug;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class DrugDTOAssembler extends RepresentationModelAssemblerSupport<Drug, DrugDTO> {
    
    public DrugDTOAssembler() {
        super(DrugController.class, DrugDTO.class);
    }

    @Override
    public DrugDTO toModel(Drug entity) {
        DrugDTO dto = instantiateModel(entity);

        dto.add(linkTo(methodOn(DrugController.class).get(entity.getId())).withSelfRel());
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setScore(entity.getScore());

        return dto;
    }
}
