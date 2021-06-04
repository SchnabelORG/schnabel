package com.schnabel.schnabel.drugs.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnabel.schnabel.drugs.dto.DrugPriceDTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("drugprice")
@Relation(collectionRelation = "drugprices")
@JsonInclude(JsonInclude.Include.ALWAYS)
@EqualsAndHashCode(callSuper = false)
public class DrugPriceDTO extends RepresentationModel<DrugPriceDTO>{
    private Long id;
    private double price;
}
