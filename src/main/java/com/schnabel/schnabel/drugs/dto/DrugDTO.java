package com.schnabel.schnabel.drugs.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnabel.schnabel.drugs.model.enums.DrugState;
import com.schnabel.schnabel.drugs.model.enums.DrugOrigin;
import com.schnabel.schnabel.drugs.model.enums.DrugType;
import com.schnabel.schnabel.drugs.model.enums.IssuingType;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;



import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Drug DTO for JSON representation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("drug")
@Relation(collectionRelation = "drugs")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugDTO extends RepresentationModel<DrugDTO> {
    private Long id;
    private String code;
    private String name;
    private String description;
    private DrugType drugType;
    private DrugState drugState;
    private DrugOrigin drugOrigin;
    private String producer;
    private String dosage;
    private IssuingType issuingType;
    private double score;
}
