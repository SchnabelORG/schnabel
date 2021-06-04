package com.schnabel.schnabel.drugs.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DrugPriceRequest {
    private double price;
    private LocalDate priceStartDate;
    private LocalDate priceEndDate;
    private Long wareHouseItemId;
}
