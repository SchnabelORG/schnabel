package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.PharmacyAdmin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 * PharmacyAdmin Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "pharmacyadmins", path = "pharmacyadmin")
public interface IPharmacyAdminRepository extends JpaRepository<PharmacyAdmin, Long>
{
    Page<PharmacyAdmin> findByPharmacyId(Pageable pageable, Long id);
}

