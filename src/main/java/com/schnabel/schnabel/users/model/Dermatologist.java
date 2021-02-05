package com.schnabel.schnabel.users.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Dermatologist user
 */
@Entity
@DiscriminatorValue("Dermatologist")
@Getter
@Setter
@EqualsAndHashCode
public class Dermatologist extends EmployedUser
{
    @ManyToMany(mappedBy = "dermatologists")
    private final Set<Pharmacy> pharmacies = new HashSet<Pharmacy>();

    public Dermatologist(String name, String surname, String email, String password, Address address)
    {
        super(name, surname, email, password, address);
    }
}
