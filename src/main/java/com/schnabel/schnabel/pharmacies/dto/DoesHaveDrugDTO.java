package com.schnabel.schnabel.pharmacies.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoesHaveDrugDTO {
    private Long drugId;
    private int quantity;
    private Long pharmacyId;
}
