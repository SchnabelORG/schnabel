package com.schnabel.schnabel.drugs.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnabel.schnabel.drugs.model.enums.DrugState;
import com.schnabel.schnabel.drugs.model.enums.DrugOrigin;
import com.schnabel.schnabel.drugs.model.enums.DrugType;
import com.schnabel.schnabel.drugs.model.enums.IssuingType;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("drugupdate")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class DrugUpdateDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private DrugState drugState;
    private DrugOrigin drugOrigin;
    private DrugType drugType;
    private String producer;
    private String dosage;
    private IssuingType issuingType;
    private List<Long> substituteDrugs;
}
