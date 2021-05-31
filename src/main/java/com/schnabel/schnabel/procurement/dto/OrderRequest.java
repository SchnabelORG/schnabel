package com.schnabel.schnabel.procurement.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderRequest {
    private String description;
    private LocalDate deadline;
    private List<OrderItemRequest> orderItems;
}
