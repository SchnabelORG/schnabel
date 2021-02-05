package com.schnabel.schnabel.pharmacies.model;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.users.model.EmployedUser;
import com.schnabel.schnabel.users.model.Patient;

import lombok.*;

import javax.persistence.Embedded;
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
    @Embedded
    private Period period;

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
