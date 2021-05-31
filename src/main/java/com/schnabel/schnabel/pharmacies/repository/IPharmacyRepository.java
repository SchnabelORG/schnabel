package com.schnabel.schnabel.pharmacies.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Pharmacy CRUD repository interface
 */
public interface IPharmacyRepository extends JpaRepository<Pharmacy, Long>, JpaSpecificationExecutor<Pharmacy>
{
    Optional<Pharmacy> findByName(String name);

    @Query(value = "SELECT p.id, p.city, p.postcode, p.street, p.street_no, p.name, p.score"
            + " FROM pharmacies p"
            + " INNER JOIN appointments a"
            + " ON p.id = a.pharmacy_id"
            + " WHERE a.free = 'T'"
            + " AND a.start_time <= :start"
            + " AND a.end_time >= :start"
            + " GROUP BY p.id",
        nativeQuery = true)
    Page<Pharmacy> findByFreePharmacistAppointment(@Param("start") LocalDateTime start, Pageable pageable);
}
