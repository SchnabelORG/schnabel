package com.schnabel.schnabel.users.dto;

import com.schnabel.schnabel.pharmacies.controller.PharmacyController;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.controller.PharmacistController;
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
        Pharmacy pharmacy = entity.getPharmacy();
        dto.setPharmacy(PharmacyDTO.builder()
            .id(pharmacy.getId())
            .name(pharmacy.getName())
            .address(pharmacy.getAddress())
            .build()
            .add(linkTo(methodOn(PharmacyController.class).getById(pharmacy.getId())).withSelfRel()));
        return dto;
    }
}
