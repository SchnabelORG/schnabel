package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.UserS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserssRepository extends JpaRepository<UserS, Long> {
    Optional<UserS> findByEmail(String email);
    Boolean existsByEmail(String email);
}
