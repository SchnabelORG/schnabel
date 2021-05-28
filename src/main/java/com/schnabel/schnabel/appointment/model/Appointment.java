package com.schnabel.schnabel.appointment.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.model.MedicalEmployee;
import com.schnabel.schnabel.users.model.Patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Appointment
 */
@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Appointment implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double price;

    @Embedded
    private Period period;

    @Column(nullable = false)
    private boolean free;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    @ManyToOne
    @JoinColumn(name = "medical_employee_id")
    private MedicalEmployee medicalEmployee;

    @OneToOne(mappedBy = "appointment")
    private AppointmentReport report;


    public Appointment(double price, Period period, boolean free, MedicalEmployee medicalEmployee, Pharmacy pharmacy)
    {
        this.price = price;
        this.period = period;
        this.free = free;
        this.medicalEmployee = medicalEmployee;
        this.pharmacy = pharmacy;
    }
}