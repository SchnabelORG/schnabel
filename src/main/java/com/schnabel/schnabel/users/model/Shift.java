package com.schnabel.schnabel.users.model;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import java.time.LocalTime;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "shifts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Shift implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalTime startTime;
    
    @Column(nullable = false)
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_employee_id")
    private MedicalEmployee medicalEmployee;

    public Shift(LocalTime startTime, LocalTime endTime, MedicalEmployee medicalEmployee)
    {
        this.medicalEmployee = medicalEmployee;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
