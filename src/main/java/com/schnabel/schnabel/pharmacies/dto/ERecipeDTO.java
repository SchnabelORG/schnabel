package com.schnabel.schnabel.pharmacies.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ERecipeDTO {
    private String name;
    private String surname;
    private LocalDate date;
    private List<ERecipeDrugDTO> drugs;
}
