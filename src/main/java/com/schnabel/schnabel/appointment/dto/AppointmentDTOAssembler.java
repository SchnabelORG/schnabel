package com.schnabel.schnabel.appointment.dto;

import com.schnabel.schnabel.appointment.controller.AppointmentController;
import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.users.controller.PatientController;
import com.schnabel.schnabel.users.controller.PharmacistController;
import com.schnabel.schnabel.users.dto.PatientDTO;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.model.MedicalEmployee;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.model.Pharmacist;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Assembles AppointmentDTO
 */
@Component
public class AppointmentDTOAssembler extends RepresentationModelAssemblerSupport<Appointment, AppointmentDTO> {
    public AppointmentDTOAssembler() {
        super(AppointmentController.class, AppointmentDTO.class);
    }


    @Override
    public AppointmentDTO toModel(Appointment entity) {
        AppointmentDTO dto = instantiateModel(entity);

        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        dto.setPeriod(entity.getPeriod());
        dto.setFree(entity.isFree());
        dto.setMedicalEmployeeId(entity.getMedicalEmployee().getId());
        dto.setMedicalEmployeeName(entity.getMedicalEmployee().getName());
        dto.setMedicalEmployeeSurname(entity.getMedicalEmployee().getSurname());
        dto.setPatient(toPatientModel(entity.getPatient()));

        return dto;
    }

    private PatientDTO toPatientModel(Patient patient) {
        if (patient == null) {
            return null;
        }
        return PatientDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .surname(patient.getSurname())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .build()
                .add(linkTo(methodOn(PatientController.class).get(patient.getId())).withSelfRel());
    }

}