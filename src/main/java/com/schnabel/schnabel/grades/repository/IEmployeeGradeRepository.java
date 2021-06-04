package com.schnabel.schnabel.grades.repository;

import java.util.Optional;

import com.schnabel.schnabel.grades.model.EmployeeGrade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEmployeeGradeRepository extends JpaRepository<EmployeeGrade, Long> {
    Optional<EmployeeGrade> findByMedicalEmployeeIdAndPatientId(Long medicalEmployeeId, Long patientId);
    @Query(value = "SELECT AVG(eg.value)"
        + " FROM employee_grades eg"
        + " WHERE eg.medical_employee_id = :employee_id",
        nativeQuery = true)
    double findAvg(@Param("employee_id") Long employeeId);
}
