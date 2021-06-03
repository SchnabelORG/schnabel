package com.schnabel.schnabel.grades.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rating request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequest {
    private Long targetId;
    private int value;
}
