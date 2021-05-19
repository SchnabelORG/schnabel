package com.schnabel.schnabel.security.util;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Utility class for refresh tokens
 */
@Component
public class RefUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(RefUtils.class);

    @Value("${custom.refSecret}")
    private String refSecret;
    @Value("${custom.refExpMonth}")
    private int refExpMonth;

    public String generateRef(String email) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, refExpMonth);
        calendar.getTime();
        return Jwts.builder()
            .setSubject(email)
            .setExpiration(calendar.getTime())
            .signWith(SignatureAlgorithm.HS512, refSecret)
            .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(refSecret).parseClaimsJws(token).getBody().getSubject();
    }
    
    public boolean validate(String ref) {
        try {
            Jwts.parser().setSigningKey(refSecret).parseClaimsJws(ref);
            return true;
        } catch (Exception e) {
            logger.error("Error validating refresh token: {}", e.getMessage());
            return false;
        }
    }
}
