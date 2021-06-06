package com.schnabel.schnabel.appointment.dto;

import com.schnabel.schnabel.drugs.dto.DrugDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedMedicationDTO {
    private int durationInDays;
    private int takePerDay;
    private int quantity;
    private DrugDTO drug;

}
