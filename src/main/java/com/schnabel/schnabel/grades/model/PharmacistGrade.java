package com.schnabel.schnabel.grades.model;

import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.misc.model.IIdentifiable;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Pharmacist grade
 */
@Entity
@Table(name = "pharmacistgrades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PharmacistGrade implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private int value;

    @ManyToOne
    @JoinColumn(name = "pharmacist_id")
    private Pharmacist pharmacist;
    
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
