package com.schnabel.schnabel.auth.repository;

import java.util.Optional;

import com.schnabel.schnabel.auth.model.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByEmail(String email);
}
