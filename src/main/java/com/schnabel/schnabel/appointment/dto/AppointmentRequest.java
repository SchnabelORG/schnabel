package com.schnabel.schnabel.appointment.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AppointmentRequest 
{
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double price;
    private Long dermatologistId;    
}
