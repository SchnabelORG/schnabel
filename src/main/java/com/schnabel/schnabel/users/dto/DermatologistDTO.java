package com.schnabel.schnabel.users.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO used for JSON representation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonRootName("dermatologist")
@Relation(collectionRelation = "dermatologists")
@EqualsAndHashCode(callSuper = false)
public class DermatologistDTO extends RepresentationModel<DermatologistDTO> {
    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private Address address;
    private double score;
    private List<PharmacyDTO> pharmacies;
}
