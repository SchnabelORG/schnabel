package com.schnabel.schnabel.promotion.controller;

import java.util.Optional;

import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.promotion.dto.PromotionDTO;
import com.schnabel.schnabel.promotion.dto.PromotionDTOAssembler;
import com.schnabel.schnabel.promotion.dto.PromotionRequest;
import com.schnabel.schnabel.promotion.model.Promotion;
import com.schnabel.schnabel.promotion.service.IPromotionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final PromotionDTOAssembler promotionDTOAsm;

    @Autowired
    public PromotionController(IPromotionService promotionService, PromotionDTOAssembler promotionDTOAsm)
    {
        this.promotionService = promotionService;
        this.promotionDTOAsm = promotionDTOAsm;
    }

    /**
     * Get promotion by id
     * @return Promotion
     */
    @GetMapping("{id}")
    public ResponseEntity<PromotionDTO> get(@PathVariable Long id)
    {
        return promotionService.get(id).map(promotionDTOAsm::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

     /**
     * Create new promotion
     * @param req - Promotion registration request containing required info
     * @return OK if created, else BadRequest
     */
    @PostMapping()
    public ResponseEntity<String> createPromotion(@RequestBody PromotionRequest promotionRequest)
    {
        Optional<Promotion> promotion = promotionService.add(new Promotion(promotionRequest.getDescription(), new Period(promotionRequest.getStartTime(), promotionRequest.getEndTime())));
        return promotion.isPresent() ? 
        ResponseEntity.ok("Added")
            : ResponseEntity.badRequest().build();
    }
}