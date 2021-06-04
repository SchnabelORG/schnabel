package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.Pharmacist;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Pharmacist Jpa repository interface
 */
public interface IPharmacistRepository extends JpaRepository<Pharmacist, Long>
{
    /*
    SELECT p.*
FROM pharmacists p
WHERE NOT EXISTS (SELECT NULL
    FROM appointments a
    WHERE a.medical_employee_id = p.id
    AND a.start_time <= '2021-06-02 08:45:00'
    AND a.end_time >= '2021-06-02 09:15:00'
    AND a.free = 'F')
AND p.pharmacy_id = 1;*/
    Page<Pharmacist> findByPharmacyId(Long pharmacyId, Pageable pageable);
    @Query(value = "SELECT p.*"
            + " FROM pharmacists p"
            + " WHERE NOT EXISTS (SELECT NULL"
            + " FROM appointments a"
            + " WHERE  a.medical_employee_id = p.id"
            + " AND a.start_time <= :start"
            + " AND a.end_time >= :end"
            + " AND a.free = 'F')"
            + " AND p.pharmacy_id = :pharmacy_id",
        nativeQuery = true)
    Page<Pharmacist> findFreeByPharmacy(@Param("pharmacy_id") Long pharmacyId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);
    Optional<Pharmacist> findByEmail(String email);
}
