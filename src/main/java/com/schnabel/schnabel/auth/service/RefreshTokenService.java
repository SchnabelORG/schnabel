package com.schnabel.schnabel.auth.service;

import java.util.Optional;

import com.schnabel.schnabel.auth.model.RefreshToken;
import com.schnabel.schnabel.auth.repository.IRefreshTokenRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.security.util.RefUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService extends JpaService<RefreshToken, Long, IRefreshTokenRepository> implements IRefreshTokenService {

    private final RefUtils refUtils;

    @Autowired
    public RefreshTokenService(IRefreshTokenRepository repository, RefUtils refUtils) {
        super(repository);
        this.refUtils = refUtils;
    }

    @Override
    public Optional<RefreshToken> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<RefreshToken> generate(String email) {
        if (findByEmail(email).isPresent()) {
            return Optional.empty();
        }

        String token = refUtils.generateRef(email);
        RefreshToken rt = new RefreshToken(email, token);
        return add(rt);
    }

    @Override
    public boolean validate(String token) {
        return refUtils.validate(token);
    }

    @Override
    public String getEmailFromToken(String token) {
        return refUtils.getEmailFromToken(token);
    }
    
}
