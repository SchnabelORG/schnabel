package com.schnabel.schnabel.pharmacies.dto;

import com.schnabel.schnabel.pharmacies.controller.PharmacyController;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PharmacyDTOAssembler extends RepresentationModelAssemblerSupport<Pharmacy, PharmacyDTO> {

    public PharmacyDTOAssembler(){
        super(PharmacyController.class, PharmacyDTO.class);
    }

    @Override
    public PharmacyDTO toModel(Pharmacy entity) {
        PharmacyDTO dto = instantiateModel(entity);

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());

        return dto;
    }
}
