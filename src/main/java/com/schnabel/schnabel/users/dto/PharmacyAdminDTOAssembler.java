package com.schnabel.schnabel.users.dto;

import com.schnabel.schnabel.pharmacies.controller.PharmacyController;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.controller.PharmacyAdminController;
import com.schnabel.schnabel.users.model.PharmacyAdmin;

import com.schnabel.schnabel.pharmacies.controller.PharmacyController;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.controller.PharmacyAdminController;
import com.schnabel.schnabel.users.model.PharmacyAdmin;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Assembles PharmacyAdminDTO
 */
@Component
public class PharmacyAdminDTOAssembler extends RepresentationModelAssemblerSupport<PharmacyAdmin, PharmacyAdminDTO>
{
    public PharmacyAdminDTOAssembler() {
        super(PharmacyAdminController.class, PharmacyAdminDTO.class);
    }

    @Override
    public PharmacyAdminDTO toModel(PharmacyAdmin entity) {
        PharmacyAdminDTO dto = instantiateModel(entity);

        dto.add(linkTo(methodOn(PharmacyAdminController.class).get(entity.getId())).withSelfRel());
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());
        dto.setPassword(entity.getPassword());
        dto.setActive(entity.isActivated());
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
