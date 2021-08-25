package com.schnabel.schnabel.penalty.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.schnabel.schnabel.penalty.model.EPenaltyReason;
import com.schnabel.schnabel.users.dto.PatientDTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Penalty DTO
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("penalty")
@Relation(collectionRelation = "penalties")
@JsonInclude(Include.NON_NULL)
public class PenaltyDTO extends RepresentationModel<PenaltyDTO> {
    private Long id;
    private PatientDTO patient;
    private String date;
    private EPenaltyReason reason;
}
