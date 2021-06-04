package com.schnabel.schnabel.security.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.schnabel.schnabel.security.service.SchnabelUserDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
        List<String> authorities = userPrincipal.getAuthorities().stream()
                .map(role -> role.toString())
                .collect(Collectors.toList());
        return buildJws(userPrincipal.getEmail(), userPrincipal.getPassword(), authorities.get(0));
    }

    public String regenerateJws(String oldJws) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(oldJws).getBody();
            return buildJws(claims.getSubject(), claims.get("password", String.class), claims.get("authorities", String.class));
        } catch (ExpiredJwtException ignore) {
            claims = ignore.getClaims();
            return buildJws(claims.getSubject(), claims.get("password", String.class),  claims.get("authorities", String.class));
        }
    }

    private String buildJws(String email, String password, String authority) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, jwtExpMin);
        return Jwts.builder()
            .setSubject(email)
            .claim("password", password)
            .claim("authorities", authority)
            .setIssuedAt(new Date())
            .setExpiration(calendar.getTime())
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public String getRoleFromJws(String jws) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jws).getBody().get("authorities", String.class);
    }

    public String getEmailFromJws(String jws) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jws).getBody().getSubject();
    }

    public String getEmailFromAuth(String authHeader) {
        return getEmailFromJws(parseJwtFromAuthorizationHeader(authHeader));
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

    public String parseJwtFromAuthorizationHeader(String authorizationHeader) {
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7, authorizationHeader.length());
        }

        return null;
    }
}
