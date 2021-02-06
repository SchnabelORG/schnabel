package com.schnabel.schnabel.consultation.dto;

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
public class ConsultationDTO
{
    private LocalDateTime startSchedule;
    private Long pharmacistId;
    private Long patientId;
}
