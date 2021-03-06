package com.schnabel.schnabel.users.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ShiftRequest {
    private Long medicalEmployeeId;
    private LocalTime startTime;
    private LocalTime endTime;
}
