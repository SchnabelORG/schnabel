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

    @Query(value = "SELECT ph.*"
            + " FROM pharmacies ph"
            + " WHERE NOT EXISTS (SELECT NULL"
            + " FROM pharmacists p"
            + " INNER JOIN appointments a"
            + " ON p.id = a.medical_employee_id"
            + " AND ph.id = p.pharmacy_id"
            + " AND a.start_time <= :start"
            + " AND a.end_time >= :end)",
        nativeQuery = true)
    Page<Pharmacy> findByFreePharmacistAppointment(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

    @Query(value = "SELECT ph.*"
        + " FROM pharmacies ph"
        + " INNER JOIN warehouseitem w"
        + " ON ph.id = w.pharmacy_id"
        + " WHERE w.drug_id = :drug_id"
        + " AND w.available > 0"
        + " GROUP BY ph.id",
        nativeQuery = true)
    Page<Pharmacy> findWithStock(@Param("drug_id") Long drugId, Pageable pageable);
}
