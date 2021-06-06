package com.schnabel.schnabel.drugs.repository;

import java.util.Optional;

import com.schnabel.schnabel.drugs.model.Drug;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Drug Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "drugs", path = "drug")
public interface IDrugRepository extends JpaRepository<Drug, Long>, JpaSpecificationExecutor<Drug>
{
    @Query(value = "SELECT d.*"
        + " FROM drugs d"
        + " INNER JOIN drug_reservations dr"
        + " ON d.id = dr.reserved_drug_id"
        + " WHERE dr.reservation_patient_id = :patient_id"
        + " AND dr.end_time <= CURRENT_TIMESTAMP"
        + " AND dr.taken = 'T'",
        nativeQuery = true)
    Page<Drug> findGradeable(@Param("patient_id") Long patientId, Pageable pageable);       
    Optional<Drug> findByName(String name);
}
