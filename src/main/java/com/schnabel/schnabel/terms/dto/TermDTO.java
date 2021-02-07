package com.schnabel.schnabel.terms.dto;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for scheduling
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TermDTO 
{
    private LocalDateTime startTime;
    private int duration;
    private double price;
    private Long pharmacyId;
    private Long employedId;

    public TermDTO(LocalDateTime startTime, int duration, double price, Long pharmacyId, Long employedId) 
    {
        this.startTime = startTime;
        this.duration = duration;
        this.price = price;
        this.pharmacyId = pharmacyId;
        this.employedId = employedId;
    }
}
