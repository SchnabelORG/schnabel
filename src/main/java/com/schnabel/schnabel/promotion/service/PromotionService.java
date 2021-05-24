package com.schnabel.schnabel.promotion.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.promotion.model.Promotion;
import com.schnabel.schnabel.promotion.repository.IPromotionRepository;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.service.IPharmacyAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Promotion service implementation
 */
@Service
public class PromotionService extends JpaService<Promotion, Long, IPromotionRepository> implements IPromotionService
{
    private final JwtUtils jwtUtils;
    private final IPharmacyAdminService pharmacyAdminService;

    @Autowired
    public PromotionService(IPromotionRepository promotionRepository, JwtUtils jwtUtils, IPharmacyAdminService pharmacyAdminService)
    {
        super(promotionRepository);
        this.jwtUtils = jwtUtils;
        this.pharmacyAdminService = pharmacyAdminService;
    }

    @Override
    public boolean createPromotion(String description, LocalDateTime startTime, LocalDateTime endTime, String authHeader) 
    {
        //Optional<PharmacyAdmin> pharmacyAdmin = getPharmacyByPharmacyAdmin(authHeader);
        
        Promotion newPromotion = new Promotion(description, new Period(startTime, endTime));
        Optional<Promotion> promotion = add(newPromotion);
        if(promotion.isPresent())
        {
            return true;
        }
        return false;
    }

    /*private Optional<PharmacyAdmin> getPharmacyByPharmacyAdmin(String authHeader)
    {
        Optional<PharmacyAdmin> pharmacyAdmin = null;
        try {
            String jws;
            if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
                jws = authHeader.substring(7, authHeader.length());
                if(jws != null) {
                    String email = jwtUtils.getEmailFromJws(jws);
                    pharmacyAdmin = pharmacyAdminService.findByEmail(email);
                }
            }
        } catch (Exception e) {
            
        }
        return pharmacyAdmin;
    }*/
}

