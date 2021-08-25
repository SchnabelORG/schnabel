package com.schnabel.schnabel.promotion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.schnabel.schnabel.misc.model.Period;

import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Promotion DTO for JSON representation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("promotion")
@Relation(collectionRelation = "promotions")
@JsonInclude(Include.NON_NULL)
public class PromotionDTO extends RepresentationModel<PromotionDTO> {
    private String description;
    private Period period;
}
