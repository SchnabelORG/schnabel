package com.schnabel.schnabel.drugs.dto;

import com.schnabel.schnabel.drugs.controller.DrugController;
import com.schnabel.schnabel.drugs.model.Drug;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setDrugState(entity.getDrugState());
        dto.setDrugType(entity.getDrugType());
        dto.setDrugOrigin(entity.getDrugOrigin());
        dto.setProducer(entity.getProducer());
        dto.setDosage(entity.getDosage());
        dto.setIssuingType(entity.getIssuingType());
        dto.setScore(entity.getScore());

        return dto;
    }

    public List<DrugDTO> listModel(Iterable<Drug> drugs) {
        return StreamSupport.stream(drugs.spliterator(), false)
            .map(d -> DrugDTO.builder()
                .id(d.getId())
                .name(d.getName()).build()).collect(Collectors.toList());
    }
}
