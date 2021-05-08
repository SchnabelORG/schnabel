package com.schnabel.schnabel.appointment.model;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.model.MedicalEmployee;
import com.schnabel.schnabel.users.model.Patient;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "appointments")
@Getter
@Setter
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

    // TODO(): or inheritance -> examination (dermatologist), counseling(pharmacist) then without this medicalEmployee!
    @ManyToOne
    @JoinColumn(name = "medical_employee_id")
    private MedicalEmployee medicalEmployee;
}