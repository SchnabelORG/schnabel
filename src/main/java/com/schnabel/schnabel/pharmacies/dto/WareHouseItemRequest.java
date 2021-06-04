package com.schnabel.schnabel.pharmacies.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WareHouseItemRequest {
    private String name;
    private String description;
    private Long pharmacyId;
}
