package com.schnabel.schnabel.users.dto;

import com.schnabel.schnabel.users.controller.SupplierController;
import com.schnabel.schnabel.users.model.Supplier;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Assembles SupplierDTO
 */
@Component
public class SupplierDTOAssembler extends RepresentationModelAssemblerSupport<Supplier, SupplierDTO> {
    
    public SupplierDTOAssembler() {
        super(SupplierController.class, SupplierDTO.class);
    }

    @Override
    public SupplierDTO toModel(Supplier entity) {
        SupplierDTO dto = instantiateModel(entity);

        dto.add(linkTo(methodOn(SupplierController.class).get(entity.getId())).withSelfRel());
        dto.setId(entity.getId());
        dto.setFirm(entity.getFirm());

        return dto;
    }
}
