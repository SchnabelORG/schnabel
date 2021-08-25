package com.schnabel.schnabel.users.repository;

import java.util.List;
import java.util.Optional;

import com.schnabel.schnabel.users.model.Shift;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Shift Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "shifts", path = "shift")
public interface IShiftRepository extends JpaRepository<Shift, Long>
{
    List<Shift> findByMedicalEmployeeId(Long id);
    List<Shift> findByPharmacyId(Long pharmacyId);
    @Query(value = "SELECT s.id, s.start_time, s.end_time, s.pharmacy_id, s.medical_employee_id FROM shifts s WHERE s.medical_employee_id = :medical_employee_id AND s.pharmacy_id = :pharmacy_id", nativeQuery = true)
    Optional<Shift> findShiftMedicalEmployeePharmacy(@Param("medical_employee_id") Long medicalEmployeeId, @Param("pharmacy_id") Long pharmacyId);
}
