package com.schnabel.schnabel.appointment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewAppointmentDTO {
    private Long pharmacistId;
    private Long patientId;
    private Long pharmacyId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
