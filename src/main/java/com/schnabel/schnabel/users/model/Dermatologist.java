package com.schnabel.schnabel.users.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dermatologist user
 */
@Entity
@DiscriminatorValue("Dermatologist")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Dermatologist extends EmployedUser
{
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    (
        name = "dermatologist_pharmacy",
        joinColumns = @JoinColumn(name = "dermatologist_id"),
        inverseJoinColumns = @JoinColumn(name = "pharmacy_id")
    )
    @JsonIgnore
    private final Set<Pharmacy> pharmacies = new HashSet<Pharmacy>();

    public Dermatologist(String name, String surname, String email, String password, Address address)
    {
        super(name, surname, email, password, address);
    }
}
