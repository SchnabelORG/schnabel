package com.schnabel.schnabel.procurement.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName("order")
public class OrderForOfferDTO {
    private Long id;
    private LocalDate deadline;
    private String description;
    private List<OrderItemDTO> orderItems;

}
