package com.schnabel.schnabel.terms.model;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.model.EmployedUser;
import com.schnabel.schnabel.users.model.Patient;

import lombok.*;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represents schedules
 */
@Entity
@Table(name = "terms")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Term implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime start;
    @Column(nullable = false)
    private int duration;
    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private Pharmacy pharmacy;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployedUser employedUser;
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

}
