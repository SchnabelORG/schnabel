package com.schnabel.schnabel.procurement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferCreationDTO {
    private int price;
    private LocalDate dateOfDelivery;
    private long orderId;
}