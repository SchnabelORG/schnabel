package com.schnabel.schnabel.users.dto;

import com.schnabel.schnabel.users.controller.PatientController;
import com.schnabel.schnabel.users.controller.PharmacistController;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.model.Pharmacist;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Assembles PharmacistDTO
 */
@Component
public class PharmacistDTOAssembler extends RepresentationModelAssemblerSupport<Pharmacist, PharmacistDTO> {

    public PharmacistDTOAssembler() {
        super(PharmacistController.class, PharmacistDTO.class);
    }

    @Override
    public PharmacistDTO toModel(Pharmacist entity) {
        PharmacistDTO dto = instantiateModel(entity);

        dto.add(linkTo(methodOn(PharmacistController.class).get(entity.getId())).withSelfRel());
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setAddress(entity.getAddress());
        return dto;
    }
}