package com.schnabel.schnabel.promotion.controller;

import com.schnabel.schnabel.promotion.service.IPromotionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Promotion REST controller
 */
@RestController
@RequestMapping("api/promotion")
public class PromotionController
{
    private final IPromotionService promotionService;

    @Autowired
    public PromotionController(IPromotionService promotionService)
    {
        this.promotionService = promotionService;
    }
}

