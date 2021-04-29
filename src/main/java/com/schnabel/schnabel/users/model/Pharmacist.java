package com.schnabel.schnabel.users.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import lombok.*;

import java.time.LocalDate;

/**
 * Pharmacist user
 */
@Entity
@DiscriminatorValue("Pharmacist")
@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode
public class Pharmacist extends User
{
    //TODO():
    //private Pharmacy pharmacy;

    public Pharmacist(String name, String surname, LocalDate dateOfBirth, String email, String password, Address address/*, Pharmacy pharmacy*/)
    {
        super(name, surname, dateOfBirth, email, password, address);
        //this.pharmacy = pharmacy;
    }

    public Pharmacist(long id, String name, String surname, LocalDate dateOfBirth, String email, String password, Address address/*, Pharmacy pharmacy*/)
    {
        super(id, name, surname, dateOfBirth, email, password, address);
        //this.pharmacy = pharmacy;
    }
}
