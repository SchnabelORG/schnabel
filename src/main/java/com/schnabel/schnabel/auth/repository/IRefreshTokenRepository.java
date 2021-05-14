package com.schnabel.schnabel.auth.repository;

import java.util.Optional;

import com.schnabel.schnabel.auth.model.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
    @Query("SELECT rt FROM patients rt WHERE rt.email = ?1")
    Optional<RefreshToken> findByEmail(String email);
}
