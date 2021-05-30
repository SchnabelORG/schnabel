package com.schnabel.schnabel.drugs.dto;


import com.schnabel.schnabel.drugs.controller.DrugReservationController;
import com.schnabel.schnabel.drugs.model.DrugReservation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DrugReservationAssembler extends RepresentationModelAssemblerSupport<DrugReservation, DrugReservationDTO> {
    public DrugReservationAssembler() {
        super(DrugReservationController.class, DrugReservationDTO.class);
    }


    @Override
    public DrugReservationDTO toModel(DrugReservation entity) {
        DrugReservationDTO dto = instantiateModel(entity);

        dto.add(linkTo(methodOn(DrugReservationController.class).get(entity.getId())).withSelfRel());
        dto.setId(entity.getId());
        dto.setDrugName(entity.getDrug().getName());
        dto.setQuantity(entity.getQuantity());
        dto.setReservationDate(entity.getReservationDate());
        dto.setEndOfReservation(entity.getEndOfReservation());
        dto.setPatientName(entity.getPatient().getName());
        dto.setPatientSurname(entity.getPatient().getSurname());
        dto.setPatientEmail(entity.getPatient().getEmail());
        dto.setTaken(entity.isTaken());


        return dto;
    }
}
