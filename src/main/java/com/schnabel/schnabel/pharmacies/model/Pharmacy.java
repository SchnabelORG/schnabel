package com.schnabel.schnabel.pharmacies.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.misc.model.IIdentifiable;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id",
    scope = Long.class)
public class Pharmacy implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Embedded
    private Address address;
    // TODO(Jovan): Use wrapper class?
    @Column(name = "avg_rating", nullable = false)
    private double avgRating;
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="pharmacy")
    private final List<Pharmacist> pharmacists = new ArrayList<Pharmacist>();
    // TODO(Jovan): When lazy loading is fixed
    // @OneToMany
    // private final List<SpecialOffer> specialOffers = new ArrayList<SpecialOffer>();
    // TODO(Jovan): Available drug list


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
