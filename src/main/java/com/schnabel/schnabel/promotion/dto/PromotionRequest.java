package com.schnabel.schnabel.promotion.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PromotionRequest {
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
