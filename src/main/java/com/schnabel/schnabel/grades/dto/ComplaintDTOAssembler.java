package com.schnabel.schnabel.grades.dto;

import com.schnabel.schnabel.grades.controller.ComplaintController;
import com.schnabel.schnabel.grades.model.Complaint;
import com.schnabel.schnabel.users.controller.PatientController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ComplaintDTOAssembler extends RepresentationModelAssemblerSupport<Complaint, ComplaintDTO> {


    public ComplaintDTOAssembler()
    {
        super(ComplaintController.class, ComplaintDTO.class);
    }

    @Override
    public ComplaintDTO toModel(Complaint entity) {
        ComplaintDTO dto = instantiateModel(entity);

        dto.setId(entity.getId());
        dto.setComplaintText(entity.getComplaintText());
        dto.setResponse(entity.getResponse());
        dto.add(linkTo(methodOn(PatientController.class).get(entity.getPatient().getId())).withRel("patient"));

        return dto;
    }
}
