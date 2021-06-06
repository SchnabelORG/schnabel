package com.schnabel.schnabel.users.dto;


import com.schnabel.schnabel.misc.model.Period;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewVacationDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long employeeId;
    private Long pharmacyId;
}
