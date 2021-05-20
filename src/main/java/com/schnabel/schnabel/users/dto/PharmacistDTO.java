package com.schnabel.schnabel.users.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;

/**
 * Pharmacist DTO for JSON representation
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("pharmacist")
@Relation(collectionRelation = "pharmacists")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PharmacistDTO  extends RepresentationModel<PharmacistDTO> {
    private Long id;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String password;
    private String email;
    private Address address;
    private PharmacyDTO pharmacy;
}
