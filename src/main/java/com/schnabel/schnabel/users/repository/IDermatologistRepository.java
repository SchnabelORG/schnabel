package com.schnabel.schnabel.users.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import com.schnabel.schnabel.users.model.Dermatologist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * Dermatologist Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "dermatologists", path = "dermatologist")
public interface IDermatologistRepository extends JpaRepository<Dermatologist, Long>
{
    Optional<Dermatologist> findByEmail(String email);

    /*@Query(value = "SELECT d.id, d.name, d.surname, d.password, d.city, d.postcode, d.street, d.street_no, d.email, d.is_activated"
    + " FROM dermatologists d"
    + " INNER JOIN dermatologist_pharmacy dp"
    + " ON d.id = dp.dermatologist_id AND dp.pharmacy_id = pharmacyId", nativeQuery = true)
    Page<Dermatologist> findAllDermatologistsPharmacy(@Param("pharmacyId") Long id, Pageable pageable);*/

    @Query(value = "SELECT d.id, d.name, d.surname, d.password, d.city, d.postcode, d.street, d.street_no, d.email, d.is_activated"
    + " FROM dermatologists d"
    + " INNER JOIN dermatologist_pharmacy dp"
    + " ON d.id = dp.dermatologist_id", nativeQuery = true)
    Page<Dermatologist> findAllDermatologistsPharmacy(Long id, Pageable pageable);
}