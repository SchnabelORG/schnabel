package com.schnabel.schnabel.promotion.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.promotion.model.Promotion;
import com.schnabel.schnabel.promotion.repository.IPromotionRepository;
import com.schnabel.schnabel.users.service.IPharmacyAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Promotion service implementation
 */
@Service
public class PromotionService extends JpaService<Promotion, Long, IPromotionRepository> implements IPromotionService
{
    private final IPharmacyAdminService pharmacyAdminService;

    @Autowired
    public PromotionService(IPromotionRepository promotionRepository, IPharmacyAdminService pharmacyAdminService)
    {
        super(promotionRepository);
        this.pharmacyAdminService = pharmacyAdminService;
    }

    @Override
    public boolean createPromotion(String description, LocalDateTime startTime, LocalDateTime endTime, String email) 
    {
        Promotion newPromotion = new Promotion(description, new Period(startTime, endTime), pharmacyAdminService.findByEmail(email).get().getPharmacy());
        Optional<Promotion> promotion = add(newPromotion);
        if(promotion.isPresent())
        {
            return true;
        }
        return false;
    }
}

