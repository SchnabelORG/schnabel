package com.schnabel.schnabel.users.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.schnabel.schnabel.misc.model.Address;

import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Patient DTO for JSON representation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("patient")
@Relation(collectionRelation = "patients")
@JsonInclude(Include.NON_NULL)
public class PatientDTO extends RepresentationModel<PatientDTO> {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private Address address;
    private List<AllergyDTO> allergies;
}
