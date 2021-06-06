package com.schnabel.schnabel.users.repository;

import java.util.List;

import com.schnabel.schnabel.users.model.Vacation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * Vacation Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "vacations", path = "vacation")
public interface IVacationRepository extends JpaRepository<Vacation, Long>
{
    @Query("SELECT v FROM Vacation v WHERE pharmacy_id = :pharmacy_id AND vacation_status = 'CREATED'")
    Page<Vacation> findCreatedVacations(@Param("pharmacy_id") Long pharmacyId, Pageable pageable);
    @Query("SELECT v FROM Vacation v WHERE medical_employee_id = :medical_employee_id AND vacation_status = 'ACCEPTED'")
    List<Vacation> findByMedicalEmployeeId(@Param("medical_employee_id") Long id);
}
