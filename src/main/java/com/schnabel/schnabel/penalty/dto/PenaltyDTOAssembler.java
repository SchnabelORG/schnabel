package com.schnabel.schnabel.penalty.dto;

import com.schnabel.schnabel.penalty.controller.PenaltyController;
import com.schnabel.schnabel.penalty.model.Penalty;
import com.schnabel.schnabel.users.dto.PatientDTO;
import com.schnabel.schnabel.users.model.Patient;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.format.DateTimeFormatter;

@Component
public class PenaltyDTOAssembler extends RepresentationModelAssemblerSupport<Penalty, PenaltyDTO> {

    public PenaltyDTOAssembler() {
        super(PenaltyController.class, PenaltyDTO.class);
    }
    @Override
    public PenaltyDTO toModel(Penalty entity) {
        PenaltyDTO dto = instantiateModel(entity);
        dto.add(linkTo(methodOn(PenaltyController.class).get(entity.getId())).withSelfRel());
        dto.setId(entity.getId());
        Patient patient = entity.getPatient();
        // NOTE(Jovan): Not setting allergies, as there's no need
        dto.setPatient(PatientDTO.builder()
            .id(patient.getId())
            .name(patient.getName())
            .surname(patient.getSurname())
            .email(patient.getEmail())
            .address(patient.getAddress()).build());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dto.setDate(entity.getDate().format(dateFormatter));
        dto.setReason(entity.getReason());
        return dto;
    }
    
}
