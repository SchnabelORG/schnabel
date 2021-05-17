package com.schnabel.schnabel.pharmacies.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.schnabel.schnabel.misc.model.Address;

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
@EqualsAndHashCode(callSuper = false)
@JsonRootName("pharmacy")
@Relation(collectionRelation = "pharmacies")
@JsonInclude(Include.NON_NULL)
public class PharmacyDTO extends RepresentationModel<PharmacyDTO> {
    private String name;
    private Address address;
    // TODO(Jovan): Add
    // private WareHouseDTO wareHouse;
    // private List<DermatologistDTO> dermatologists;
    // private List<PharmacistDTO> pharmacists;
}
