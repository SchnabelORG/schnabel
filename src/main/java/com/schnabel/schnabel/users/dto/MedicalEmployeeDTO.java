package com.schnabel.schnabel.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonRootName("employee")
@Relation(collectionRelation = "employees")
@JsonInclude(Include.NON_NULL)
public class MedicalEmployeeDTO extends RepresentationModel<MedicalEmployeeDTO> {
    private Long id;
    private String name;
    private String surname;
    private double score;
    private String email;
    private boolean isDefaultPasswordChanged;
}
