package com.schnabel.schnabel.drugs.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * AvailabilityRequest DTO for JSON representation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("availabilityrequest")
@Relation(collectionRelation = "availabilityrequests")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvailabilityRequestDTO extends RepresentationModel<AvailabilityRequestDTO>{
    private int quantity;
    private String name;
    private String surname;
    private String drugName;
}
