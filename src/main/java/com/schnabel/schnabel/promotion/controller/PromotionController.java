package com.schnabel.schnabel.promotion.controller;

import com.schnabel.schnabel.promotion.dto.PromotionRequest;
import com.schnabel.schnabel.promotion.service.IPromotionService;
import com.schnabel.schnabel.security.util.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    private final JwtUtils jwtUtils;

    @Autowired
    public PromotionController(IPromotionService promotionService, JwtUtils jwtUtils)
    {
        this.promotionService = promotionService;
        this.jwtUtils = jwtUtils;
    }

     /**
     * Create new promotion
     * @param req - Creating promotion request containing required info
     * @return OK if created, else BadRequest
     */
    @PostMapping()
    public ResponseEntity<String> createPromotion(@RequestBody PromotionRequest promotionRequest, @RequestHeader("Authorization") String authHeader)
    {
        String jws = jwtUtils.parseJwtFromAuthorizationHeader(authHeader);
        return promotionService.createPromotion(promotionRequest.getDescription(), promotionRequest.getStartTime(), promotionRequest.getEndTime(), jwtUtils.getEmailFromJws(jws))? 
        ResponseEntity.ok("Added")
            : ResponseEntity.badRequest().build();
    }
}