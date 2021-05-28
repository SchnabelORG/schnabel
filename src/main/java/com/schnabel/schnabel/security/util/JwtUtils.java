package com.schnabel.schnabel.security.util;

import java.util.Calendar;
import java.util.Date;

import com.schnabel.schnabel.security.service.SchnabelUserDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
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

    @Value("${custom.jwtExpMin}")
    private int jwtExpMin;

    public String generateJws(Authentication authentication) {
        SchnabelUserDetails userPrincipal = (SchnabelUserDetails) authentication.getPrincipal();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, jwtExpMin);
        return Jwts.builder()
            .setSubject(userPrincipal.getEmail())
            .claim("password", userPrincipal.getPassword())
            .setIssuedAt(new Date())
            //.setExpiration(new Date(1639263600000L))
            .setExpiration(calendar.getTime())
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public String getEmailFromJws(String jws) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jws).getBody().getSubject();
    }

    public UsernamePasswordAuthenticationToken getUPAT(String jws) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jws).getBody();
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), claims.get("password"));
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
