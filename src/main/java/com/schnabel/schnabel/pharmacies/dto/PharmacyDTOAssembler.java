package com.schnabel.schnabel.pharmacies.dto;

import com.schnabel.schnabel.pharmacies.controller.PharmacyController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.controller.PharmacistController;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.model.Pharmacist;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Assembles PharmacyDTO for JSON representation
 */
@Component
public class PharmacyDTOAssembler extends RepresentationModelAssemblerSupport<Pharmacy, PharmacyDTO> {

    public PharmacyDTOAssembler() {
        super(PharmacyController.class, PharmacyDTO.class);
    }

    @Override
    public PharmacyDTO toModel(Pharmacy entity) {
        PharmacyDTO dto = instantiateModel(entity);

        dto.add(linkTo(methodOn(PharmacyController.class).getById(entity.getId())).withSelfRel());
        dto.setAddress(entity.getAddress());
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.add(linkTo(methodOn(PharmacistController.class).getByPharmacyId(entity.getId(), Pageable.unpaged())).withRel("pharmacies"));
        return dto;
    }

    private List<PharmacistDTO> toPharmacistModel(List<Pharmacist> pharmacists) {
        if(pharmacists.isEmpty()) {
            return Collections.emptyList();
        }

        return pharmacists.stream()
            .map(p -> PharmacistDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .surname(p.getSurname())
                .email(p.getEmail())
                .address(p.getAddress())
                .build()
                .add(linkTo(
                    methodOn(PharmacistController.class)
                    .get(p.getId()))
                .withSelfRel()))
            .collect(Collectors.toList());
    }
    
}
