package com.schnabel.schnabel.pharmacies.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyDrugDTO {
    private Long id;
    private String name;
    private String city;
    private String street;
    private double price;

    public PharmacyDrugDTO(Long id, String name, String city, String street) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
    }
}
