package com.schnabel.schnabel.users.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.schnabel.schnabel.pharmacies.controller.PharmacyController;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.controller.DermatologistController;
import com.schnabel.schnabel.users.model.Dermatologist;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

@Component
public class DermatologistDTOAssembler extends RepresentationModelAssemblerSupport<Dermatologist, DermatologistDTO> {

    public DermatologistDTOAssembler() {
        super(DermatologistController.class, DermatologistDTO.class);
    }

    @Override
    public DermatologistDTO toModel(Dermatologist entity) {
        DermatologistDTO dto = instantiateModel(entity);
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setAddress(entity.getAddress());
        dto.setScore(entity.getScore());
        dto.setPharmacies(toPharmaciesModel(entity.getPharmacies()));
        return dto;
    }

    private List<PharmacyDTO> toPharmaciesModel(List<Pharmacy> pharmacies) {
        if (pharmacies.isEmpty()) {
            return Collections.emptyList();
        }
        
        return pharmacies.stream()
            .map(p -> PharmacyDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .address(p.getAddress())
                .build()
                .add(linkTo(
                    methodOn(PharmacyController.class)
                    .getById(p.getId()))
                .withSelfRel())).collect(Collectors.toList());
    }
    
}
