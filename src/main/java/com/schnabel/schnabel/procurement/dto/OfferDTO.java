package com.schnabel.schnabel.procurement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnabel.schnabel.users.dto.SupplierDTO;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("offer")
@Relation(collectionRelation = "offers")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferDTO extends RepresentationModel<OfferDTO> {
    private Long id;
    private Integer price;
    private LocalDate dateOfDelivery;
    private SupplierDTO supplier;
}
