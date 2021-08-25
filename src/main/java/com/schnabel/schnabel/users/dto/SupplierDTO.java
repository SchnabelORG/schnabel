package com.schnabel.schnabel.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
<<<<<<< HEAD
import com.schnabel.schnabel.misc.model.Address;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

=======

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
>>>>>>> develop

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("supplier")
@Relation(collectionRelation = "suppliers")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class SupplierDTO extends RepresentationModel<SupplierDTO> {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private Address address;
    private String company;
    private String firm;
}
