package com.schnabel.schnabel.procurement.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnabel.schnabel.drugs.dto.DrugForOfferDTO;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("orderItem")
@Relation(collectionRelation = "orderitems")
public class OrderItemForOfferDTO extends RepresentationModel<OrderItemForOfferDTO> {
    private Long id;
    private int quantity;
    private String drug;
}
