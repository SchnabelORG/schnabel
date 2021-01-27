package com.schnabel.schnabel.pharmacies.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.schnabel.misc.Address;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "pharmacies")
@EqualsAndHashCode
public class Pharmacy
{
    @Id
    private int id;
    private String name;
    @Embedded
    private Address address;
    // TODO(Jovan): Use wrapper class?
    private double avgRating;
    // TODO(Jovan): Dermatologist list
    // TODO(Jovan): Available dermatologist appointments list
    // TODO(Jovan): Pharmacist list
    // TODO(Jovan): Available drug list

    public Pharmacy()
    {
        this.id = 1;
        this.name = "Placholdername";
        this.address = new Address();
        this.avgRating = 5.0;
    }

    public Pharmacy(String name, Address address)
    {
        this.name = name;
        this.address = address;
        this.avgRating = 0.0;
    }
}
