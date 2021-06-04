package com.schnabel.schnabel.grades.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.users.model.MedicalEmployee;
import com.schnabel.schnabel.users.model.Patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee_grades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmployeeGrade implements IIdentifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(0)
    @Max(5)
    private int value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_employee_id")
    private MedicalEmployee medicalEmployee;

    @OneToOne(fetch = FetchType.LAZY)
    private Patient patient;

    public EmployeeGrade(int value, MedicalEmployee medicalEmployee, Patient patient) {
        this.value = value;
        this.medicalEmployee = medicalEmployee;
        this.patient = patient;
    }
}
