package com.schnabel.schnabel.users.dto;

import com.schnabel.schnabel.users.controller.PatientController;
import com.schnabel.schnabel.users.model.Patient;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

/**
 * Assembles PatientDTO
 */
@Component
public class PatientDTOAssembler extends RepresentationModelAssemblerSupport<Patient, PatientDTO> {

    public PatientDTOAssembler() {
        super(PatientController.class, PatientDTO.class);
    }

    @Override
    public PatientDTO toModel(Patient entity) {
        PatientDTO dto = instantiateModel(entity);

        dto.add(linkTo(methodOn(PatientController.class).get(entity.getId())).withSelfRel());
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());
        dto.setPhoneNo(entity.getPhoneNo());

        return dto;
    }



    // @Override
    // public CollectionModel<PatientDTO> toCollectionModel(Iterable<? extends Patient> entities) {
    //     CollectionModel<PatientDTO> dtos = super.toCollectionModel(entities);
    //     dtos.add(linkTo(methodOn(PatientController.class).getAll()).withSelfRel());

    //     return dtos;
    // }
    
}
