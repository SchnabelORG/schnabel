package com.schnabel.schnabel.procurement.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("order")
@Relation(collectionRelation = "orders")
public class OrderDTO extends RepresentationModel<OrderDTO> {
    private Long id;
    private LocalDate deadline;
    private String description;
    //private PharmacyAdminDTO pharmacyAdmin;
    //private OfferDTO offerDTO;
    private List<OrderItemForOfferDTO> orderItems;
}
