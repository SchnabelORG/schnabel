package com.schnabel.schnabel.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * PharmacyAdmin DTO for JSON representation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("pharmacyadmin")
@Relation(collectionRelation = "pharmacyadmins")
@JsonInclude(Include.NON_NULL)
public class PharmacyAdminDTO extends RepresentationModel<PharmacyAdminDTO> 
{
    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private Address address;
    private PharmacyDTO pharmacy;
}
