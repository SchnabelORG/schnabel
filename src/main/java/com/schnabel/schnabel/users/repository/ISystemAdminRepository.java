package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.SystemAdmin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 * SystemAdmin Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "systemadmins", path = "systemadmin")
public interface ISystemAdminRepository extends JpaRepository<SystemAdmin, Long>
{
    Optional<SystemAdmin> findByEmail(String email);
}
