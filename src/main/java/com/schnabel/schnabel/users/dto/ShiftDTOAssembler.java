package com.schnabel.schnabel.users.dto;

import com.schnabel.schnabel.users.controller.ShiftController;
import com.schnabel.schnabel.users.model.Shift;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Assembles ShiftDTO
 */
@Component
public class ShiftDTOAssembler extends RepresentationModelAssemblerSupport<Shift, ShiftDTO> {
    
    public ShiftDTOAssembler() {
        super(ShiftController.class, ShiftDTO.class);
    }

    @Override
    public ShiftDTO toModel(Shift entity) {
        ShiftDTO dto = instantiateModel(entity);

        dto.add(linkTo(methodOn(ShiftController.class).get(entity.getId())).withSelfRel());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());

        return dto;
    }
}
