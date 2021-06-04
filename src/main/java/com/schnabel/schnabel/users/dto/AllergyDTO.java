package com.schnabel.schnabel.users.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnabel.schnabel.misc.model.Address;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

/**
 * Patient DTO for JSON representation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("allergies")
@Relation(collectionRelation = "allergies")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllergyDTO extends RepresentationModel<AllergyDTO> {
    private Long id;
    private String type;
    private Long drugId;
}
