package com.schnabel.schnabel.promotion.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.promotion.dto.PromotionDTO;
import com.schnabel.schnabel.promotion.model.Promotion;

/**
 * Promotion service interface
 */
public interface IPromotionService extends IJpaService<Promotion, Long>
{
    Optional<PromotionDTO> findByIdDTO(Long id);
    boolean createPromotion(String description, LocalDateTime startTime, LocalDateTime endTime, String email);
}
