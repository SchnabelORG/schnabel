package com.schnabel.schnabel.users.dto;

import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.users.controller.AllergyController;
import com.schnabel.schnabel.users.model.Allergy;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

@Component
public class AllergyDTOAssembler extends RepresentationModelAssemblerSupport<Allergy, AllergyDTO> {

    public AllergyDTOAssembler() {
        super(AllergyController.class, AllergyDTO.class);
    }

    @Override
    public AllergyDTO toModel(Allergy entity) {
        AllergyDTO dto = instantiateModel(entity);
        dto.add(linkTo(methodOn(AllergyController.class).get(entity.getId())).withSelfRel());
        dto.setId(entity.getId());
        dto.setType(entity.getAllergyType());
        dto.setDrug(DrugDTO.builder().name(entity.getDrug().getName()).build());
        // TODO(Jovan): Add Drug / Patient as needed
        return dto;
    }
    
}
