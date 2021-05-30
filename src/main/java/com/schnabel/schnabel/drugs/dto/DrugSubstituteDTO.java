package com.schnabel.schnabel.drugs.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnabel.schnabel.drugs.model.enums.DrugType;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("substitute")
@Relation(collectionRelation = "drugs")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class DrugSubstituteDTO {
    private Long id;
    private String name;
    private DrugType drugType;
    private String producer;
    private String dosage;
}
