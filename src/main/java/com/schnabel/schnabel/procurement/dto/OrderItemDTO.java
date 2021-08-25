package com.schnabel.schnabel.procurement.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

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
public class OrderItemDTO extends RepresentationModel<OrderItemDTO> {
    private Long id;
    private int quantity;
    private String drug;
}