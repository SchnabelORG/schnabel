package com.schnabel.schnabel.drugs.dto;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.schnabel.schnabel.drugs.controller.AvailabilityRequestController;
import com.schnabel.schnabel.drugs.model.AvailabilityRequest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class AvailabilityRequestDTOAssembler extends RepresentationModelAssemblerSupport<AvailabilityRequest, AvailabilityRequestDTO> {
    
    public AvailabilityRequestDTOAssembler() {
        super(AvailabilityRequestController.class, AvailabilityRequestDTO.class);
    }

    @Override
    public AvailabilityRequestDTO toModel(AvailabilityRequest entity) {
        AvailabilityRequestDTO dto = instantiateModel(entity);

        dto.add(linkTo(methodOn(AvailabilityRequestController.class).get(entity.getId())).withSelfRel());
        dto.setQuantity(entity.getQuantity());
        dto.setName(entity.getPharmacist().getName());
        dto.setSurname(entity.getPharmacist().getSurname());
        dto.setDrugName(entity.getDrug().getName());

        return dto;
    }
    
}
