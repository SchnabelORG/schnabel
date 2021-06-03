package com.schnabel.schnabel.grades.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rating request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeRequest {
    private Long targetId;
    @Min(value = 0, message = "Must be between 0 and 5")
    @Max(value = 5, message = "Must be between 0 and 5")
    private int value;
}
