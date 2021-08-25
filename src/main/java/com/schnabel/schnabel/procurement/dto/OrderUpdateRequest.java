package com.schnabel.schnabel.procurement.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderUpdateRequest {
    private Long id;
    private String description;
    private LocalDate deadline;
}
