package com.schnabel.schnabel.appointment.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.users.dto.PatientDTO;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

/**
 * Appointment DTO for JSON representation
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("appointments")
@Relation(collectionRelation = "appointments")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentDTO extends RepresentationModel<AppointmentDTO> {
    private Long id;
    private double price;
    private Period period;
    private boolean free;
    private PatientDTO patient;
    private Long medicalEmployeeId;
    private String medicalEmployeeName;
    private String medicalEmployeeSurname;

}