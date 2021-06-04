package com.schnabel.schnabel.users.dto;

import com.schnabel.schnabel.users.controller.VacationController;
import com.schnabel.schnabel.users.model.MedicalEmployee;
import com.schnabel.schnabel.users.model.Vacation;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Assembles VacationDTO
 */
@Component
public class VacationDTOAssembler extends RepresentationModelAssemblerSupport<Vacation, VacationDTO> {
    
    public VacationDTOAssembler() {
        super(VacationController.class, VacationDTO.class);
    }

    @Override
    public VacationDTO toModel(Vacation entity) {
        VacationDTO dto = instantiateModel(entity);

        dto.add(linkTo(methodOn(VacationController.class).get(entity.getId())).withSelfRel());
        dto.setId(entity.getId());
        dto.setPeriod(entity.getPeriod());
        MedicalEmployee employee = entity.getMedicalEmployee();
        dto.setMedicalEmployee(MedicalEmployeeDTO.builder()
        .id(employee.getId())
        .name(employee.getName())
        .surname(employee.getSurname())
        .build());

        return dto;
    }
}
