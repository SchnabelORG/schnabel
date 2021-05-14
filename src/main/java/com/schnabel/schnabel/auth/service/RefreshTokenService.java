package com.schnabel.schnabel.auth.service;

import java.util.Optional;

import com.schnabel.schnabel.auth.model.RefreshToken;
import com.schnabel.schnabel.auth.repository.IRefreshTokenRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;

import org.springframework.beans.factory.annotation.Autowired;

public class RefreshTokenService extends JpaService<RefreshToken, Long, IRefreshTokenRepository> implements IRefreshTokenService {

    @Autowired
    public RefreshTokenService(IRefreshTokenRepository repository) {
        super(repository);
    }

    @Override
    public Optional<RefreshToken> findByEmail(String email) {
        return repository.findByEmail(email);
    }
    
}
