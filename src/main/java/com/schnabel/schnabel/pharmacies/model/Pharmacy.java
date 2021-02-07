package com.schnabel.schnabel.pharmacies.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.terms.model.Term;
import com.schnabel.schnabel.users.model.Pharmacist;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Pharmacy
 */
@Entity
@Table(name = "pharmacies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Pharmacy implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Embedded
    private Address address;
    // TODO(Marko): Add email?
    // TODO(Jovan): Use wrapper class?
    @Column(name = "avg_rating", nullable = false)
    private double avgRating;
    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    (
        name = "dermatologist_pharmacy",
        joinColumns = @JoinColumn(name = "pharmacy_id"),
        inverseJoinColumns = @JoinColumn(name = "dermatologist_id")
    )
    private List<Dermatologist> dermatologists;*/
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pharmacy")
    private final Set<Pharmacist> pharmacists = new HashSet<Pharmacist>();
    // TODO(Jovan): Available drug list
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pharmacy")
    private final Set<Term> terms = new HashSet<Term>();


    public Pharmacy(String name, Address address)
    {
        this.name = name;
        this.address = address;
        this.avgRating = 0.0;
    }

    public Pharmacy(Long id, String name, Address address)
    {
        this.id = id;
        this.address = address;
        this.name = name;
    }
}
