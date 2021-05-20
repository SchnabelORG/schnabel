package com.schnabel.schnabel.drugs.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.users.dto.PatientDTO;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Patient DTO for JSON representation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("dreservation")
@Relation(collectionRelation = "drugs_reservations")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugReservationDTO extends RepresentationModel<DrugReservationDTO>{
    private Long id;
    private String drugName;
    private int quantity;
    private LocalDateTime reservationDate;
    private LocalDateTime endOfReservation;
    private String patientName;
    private String patientSurname;
    private String patientEmail;
    private boolean taken;


}
