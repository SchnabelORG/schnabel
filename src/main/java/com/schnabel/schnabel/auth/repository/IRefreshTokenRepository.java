package com.schnabel.schnabel.auth.repository;

import java.util.Optional;

import com.schnabel.schnabel.auth.model.RefreshToken;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
    @Query("SELECT rt FROM refresh_tokens rt WHERE rt.email = ?1")
    Optional<RefreshToken> findByEmail(String email);
}
