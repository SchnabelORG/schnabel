package com.schnabel.schnabel.users.repository;

import java.util.Optional;

import com.schnabel.schnabel.users.model.PharmacyAdmin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * PharmacyAdmin Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "pharmacyadmins", path = "pharmacyadmin")
public interface IPharmacyAdminRepository extends JpaRepository<PharmacyAdmin, Long>
{
    Optional<PharmacyAdmin> findByEmail(String email);
}