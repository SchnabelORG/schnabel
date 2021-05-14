package com.schnabel.schnabel.security.util;

import java.util.Date;

import com.schnabel.schnabel.security.service.PatientDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWT utility class
 */
@Component
public class JwtUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${custom.jwtSecret}")
    private String jwtSecret;

    @Value("${custom.jwtExpMs}")
    private int jwtExpMs;

    public String generateJws(Authentication authentication) {
        PatientDetails patientPrincipal = (PatientDetails) authentication.getPrincipal();

        return Jwts.builder()
            .setSubject(patientPrincipal.getEmail())
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + jwtExpMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public String getEmailFromJws(String jws) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jws).getBody().getSubject();
    }

    public boolean validateJws(String jws) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jws);
            return true;
        } catch (Exception e) {
            logger.error("Error validating jws: {}", e.getMessage());
            return false;
        }
    }
}
