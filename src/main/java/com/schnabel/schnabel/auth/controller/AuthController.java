package com.schnabel.schnabel.auth.controller;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.schnabel.schnabel.auth.dto.LoginRequest;
import com.schnabel.schnabel.auth.model.RefreshToken;
import com.schnabel.schnabel.auth.service.IRefreshTokenService;
import com.schnabel.schnabel.security.util.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication REST controller
 */
@RestController
@RequestMapping("api/auth")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final IRefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, IRefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @GetMapping("refresh")
    public ResponseEntity<String> refresh(@CookieValue(value = "email", required = true, defaultValue = "") String token, @RequestHeader("Authorization") String oldJws) {
        if (token.isBlank() || !refreshTokenService.validate(token)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(jwtUtils.regenerateJws(jwtUtils.parseJwtFromAuthorizationHeader(oldJws)));
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginRequest dto, HttpServletResponse response) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jws = jwtUtils.generateJws(auth);

        Optional<RefreshToken> refreshToken = refreshTokenService.findByEmail(dto.getEmail());
        if (!refreshToken.isPresent() || !refreshTokenService.validate(refreshToken.get().getToken())) {
            return ResponseEntity.badRequest().build();
        }

        Cookie cookie = new Cookie("email", refreshToken.get().getToken());
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok(jws);
    }

    @GetMapping("role")
    public ResponseEntity<String> getRole(@RequestHeader("Authorization") String auth) {
        return ResponseEntity.ok(jwtUtils.getRoleFromJws(jwtUtils.parseJwtFromAuthorizationHeader(auth)));
    }


}
