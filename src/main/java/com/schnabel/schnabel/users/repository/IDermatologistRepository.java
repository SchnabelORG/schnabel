package com.schnabel.schnabel.users.repository;

import java.util.Optional;

import com.schnabel.schnabel.users.model.Dermatologist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * Dermatologist Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "dermatologists", path = "dermatologist")
public interface IDermatologistRepository extends JpaRepository<Dermatologist, Long>, JpaSpecificationExecutor<Dermatologist>
{
    Optional<Dermatologist> findByEmail(String email);
    @Query("SELECT DISTINCT d FROM Dermatologist d join d.pharmacies p WHERE p.id = :pharmacy_id")
    Page<Dermatologist> findAllDermatologistsPharmacy(@Param("pharmacy_id") Long id, Pageable pageable);
    @Query(value = "SELECT d.* from dermatologists d inner join dermatologist_pharmacy dp ON d.id = dp.dermatologist_id AND dp.pharmacy_id != :pharmacy_id", nativeQuery = true)
    Page<Dermatologist> findAllDermatologistNotInPharmacy(@Param("pharmacy_id") Long id, Pageable pageable);
}