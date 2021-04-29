package com.schnabel.schnabel.users.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import lombok.*;

import java.time.LocalDate;

/**
 * Dermatologist user
 */
@Entity
@DiscriminatorValue("Dermatologist")
@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode
public class Dermatologist extends User
{
    //TODO(): 
    //private List<Pharmacy> pharmacies;

    public Dermatologist(String name, String surname, LocalDate dateOfBirth, String email, String password, Address address)
    {
        super(name, surname, dateOfBirth, email, password, address);
    }
}
