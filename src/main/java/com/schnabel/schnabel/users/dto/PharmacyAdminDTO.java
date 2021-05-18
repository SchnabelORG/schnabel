package com.schnabel.schnabel.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;

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
@JsonInclude(JsonInclude.Include.ALWAYS)
public class PharmacyAdminDTO extends RepresentationModel<PharmacyAdminDTO> {
    private Long id;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String email;
    private Address address;
    private PharmacyDTO pharmacy;
}
