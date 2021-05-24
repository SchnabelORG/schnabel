package com.schnabel.schnabel.promotion.service;

import java.time.LocalDateTime;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.promotion.model.Promotion;

/**
 * Promotion service interface
 */
public interface IPromotionService extends IJpaService<Promotion, Long>
{
    public boolean createPromotion(String description, LocalDateTime startTime, LocalDateTime endTime, String authHeader);
}
