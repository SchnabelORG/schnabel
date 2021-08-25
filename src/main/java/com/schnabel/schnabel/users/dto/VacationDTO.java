package com.schnabel.schnabel.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnabel.schnabel.misc.model.Period;

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
@JsonRootName("vacation")
@Relation(collectionRelation = "vacations")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VacationDTO extends RepresentationModel<VacationDTO> {
    private Long id;
    private Period period;
    private MedicalEmployeeDTO medicalEmployee;
}
