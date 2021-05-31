package com.schnabel.schnabel.users.dto;

import com.schnabel.schnabel.misc.model.Period;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for looking up pharmacists without appointments
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreePharmacistLookupRequest {
    private Long pharmacyId;
    private Period period;
}
