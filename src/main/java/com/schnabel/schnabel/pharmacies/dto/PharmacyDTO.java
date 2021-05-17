package com.schnabel.schnabel.pharmacies.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.pharmacies.model.WareHouse;
import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.model.Pharmacist;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("pharmacy")
@Relation(collectionRelation = "pharmacies")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PharmacyDTO extends RepresentationModel<PharmacyDTO> {
    private Long id;
    private String name;
    private Address address;
}
