package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.ERole;
import com.schnabel.schnabel.users.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
