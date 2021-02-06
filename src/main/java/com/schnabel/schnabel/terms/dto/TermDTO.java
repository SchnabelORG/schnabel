package com.schnabel.schnabel.terms.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for consultation scheduling
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TermDTO 
{
    private LocalDateTime startTime;
    private int duration;
    private double price;
    private Long pharmacyId;
    private Long employedId;
}
