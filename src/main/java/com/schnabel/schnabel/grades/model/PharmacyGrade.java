package com.schnabel.schnabel.grades.model;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.misc.model.IIdentifiable;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Pharmacy grade
 */
@Entity
@Table(name = "pharmacygrades")
@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode
public class PharmacyGrade implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int value;

    //TODO():
    //private Pharmacy pharmacy;
    //private Patient patient;
}
