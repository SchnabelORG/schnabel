package com.schnabel.schnabel.auth.service;

import java.util.Optional;

import com.schnabel.schnabel.auth.model.RefreshToken;
import com.schnabel.schnabel.misc.interfaces.IJpaService;

public interface IRefreshTokenService extends IJpaService<RefreshToken, Long> {
    
    Optional<RefreshToken> findByEmail(String email);
    Optional<RefreshToken> generate(String email);
    boolean validate(String token);
    String getEmailFromToken(String token);
}
