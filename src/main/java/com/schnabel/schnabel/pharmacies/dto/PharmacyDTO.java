package com.schnabel.schnabel.pharmacies.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.users.dto.PharmacistDTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Pharmacy DTO for JSON representation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("pharmacy")
@Relation(collectionRelation = "pharmacies")
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class PharmacyDTO extends RepresentationModel<PharmacyDTO> {
    private Long id;
    private String name;
    private Address address;
    private double consultPrice;
    private int score;
    private List<PharmacistDTO> pharmacists;
}
