package com.schnabel.schnabel.users.repository;

import java.util.List;
import java.util.Optional;

import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.model.PharmacyAdmin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;

/**
 * PharmacyAdmin Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "pharmacyadmins", path = "pharmacyadmin")
public interface IPharmacyAdminRepository extends JpaRepository<PharmacyAdmin, Long>
{
    Page<PharmacyAdmin> findByPharmacyId(Pageable pageable, Long id);
    Optional<PharmacyAdmin> findByEmail(String email);
    @Query(value = "SELECT d FROM dermatologists d INNER JOIN dermatologist_pharmacy ON d.id = dermatologist_pharmacy.dermatologist_id AND dermatologist_pharmacy.pharmacy != :pharmacy_id", nativeQuery = true)
    List<Dermatologist> getDermatologistsNotPharmacy(@Param("pharmacy_id") Long pharmacyId, Pageable pageable);
}
