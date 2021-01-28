package com.schnabel.schnabel.pharmacies.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.misc.model.IIdentifiable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pharmacies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Pharmacy implements IIdentifiable<Integer>
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

    public Pharmacy(String name, Address address)
    {
        this.name = name;
        this.address = address;
        this.avgRating = 0.0;
    }

    @Override
    public Integer getId()
    {
        return this.id;
    }
}
