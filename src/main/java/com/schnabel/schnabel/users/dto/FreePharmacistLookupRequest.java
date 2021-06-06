package com.schnabel.schnabel.users.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schnabel.schnabel.misc.UnixToLocalDateTimeConverter;

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
    
    @JsonDeserialize(using = UnixToLocalDateTimeConverter.class)
    private LocalDateTime start;
}
