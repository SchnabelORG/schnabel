package com.schnabel.schnabel.users.dto;

import com.schnabel.schnabel.misc.model.Period;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Drug reservation DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugReservationRequest {
    
    private Long drugId;
    private Long pharmacyId;
    private int Quantity;
    private Period period;
}
