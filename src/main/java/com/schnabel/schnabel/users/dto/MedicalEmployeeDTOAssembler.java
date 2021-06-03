package com.schnabel.schnabel.users.dto;

import com.schnabel.schnabel.users.controller.MedicalEmployeeController;
import com.schnabel.schnabel.users.model.MedicalEmployee;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class MedicalEmployeeDTOAssembler extends RepresentationModelAssemblerSupport<MedicalEmployee, MedicalEmployeeDTO> {

    public MedicalEmployeeDTOAssembler() {
        super(MedicalEmployeeController.class, MedicalEmployeeDTO.class);
    }

    @Override
    public MedicalEmployeeDTO toModel(MedicalEmployee entity) {
        MedicalEmployeeDTO dto = instantiateModel(entity);
        dto.add(linkTo(methodOn(MedicalEmployeeController.class).get(entity.getId())).withSelfRel());
        dto.setName(entity.getName());
        dto.setScore(entity.getScore());
        return dto;
    }
    
}
