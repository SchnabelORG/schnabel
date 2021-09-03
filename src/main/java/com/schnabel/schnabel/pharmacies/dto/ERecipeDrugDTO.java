package com.schnabel.schnabel.pharmacies.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ERecipeDrugDTO {
    private String code;
    private String name;
    private int quantity;
}

