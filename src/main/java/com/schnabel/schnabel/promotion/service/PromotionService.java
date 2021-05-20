package com.schnabel.schnabel.promotion.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.promotion.model.Promotion;
import com.schnabel.schnabel.promotion.repository.IPromotionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Promotion service implementation
 */
@Service
public class PromotionService extends JpaService<Promotion, Long, IPromotionRepository> implements IPromotionService
{
    @Autowired
    public PromotionService(IPromotionRepository promotionRepository)
    {
        super(promotionRepository);
    }
}

