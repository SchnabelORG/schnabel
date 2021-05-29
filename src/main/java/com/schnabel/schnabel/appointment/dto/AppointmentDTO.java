package com.schnabel.schnabel.appointment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.users.dto.MedicalEmployeeDTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("appointment")
@Relation(collectionRelation = "appointment")
@JsonInclude(Include.NON_NULL)
public class AppointmentDTO extends RepresentationModel<AppointmentDTO>{
    private double price;
    private Period period;
    private boolean free;
    private MedicalEmployeeDTO medicalEmployee;
}
