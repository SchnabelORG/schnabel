package com.schnabel.schnabel.appointment.dto;

import com.schnabel.schnabel.appointment.controller.AppointmentController;
import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.users.dto.MedicalEmployeeDTO;
import com.schnabel.schnabel.users.model.MedicalEmployee;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.format.DateTimeFormatter;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class AppointmentDTOAssembler extends RepresentationModelAssemblerSupport<Appointment, AppointmentDTO> {

    public AppointmentDTOAssembler() {
        super(AppointmentController.class, AppointmentDTO.class);
    }

    @Override
    public AppointmentDTO toModel(Appointment entity) {
        AppointmentDTO dto = instantiateModel(entity);
    dto.add(linkTo(methodOn(AppointmentController.class).get(entity.getId())).withSelfRel());
    dto.setId(entity.getId());
    dto.setPrice(entity.getPrice());
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    dto.setDate(entity.getPeriod().getStartTime().format(dateFormatter));
    dto.setStart(entity.getPeriod().getStartTime().format(timeFormatter));
    dto.setDuration(entity.getPeriod().getDurationMinutes());
    dto.setFree(entity.isFree());
    // TODO(Jovan): Add link to controller if needed
    MedicalEmployee employee = entity.getMedicalEmployee();
    dto.setMedicalEmployee(MedicalEmployeeDTO.builder()
        .name(employee.getName())
        .build());
    
    return dto;
    }
}
