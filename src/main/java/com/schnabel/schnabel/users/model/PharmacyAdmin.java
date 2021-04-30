package com.schnabel.schnabel.users.model;

import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import lombok.*;

import java.time.LocalDate;

/**
 * Pharmacy admin user
 */
@Entity
@DiscriminatorValue("PharmacyAdmin")
@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode
public class PharmacyAdmin extends User
{
    //TODO():
    //private Pharmacy pharmacy;

    public PharmacyAdmin(String name, String surname, LocalDate dateOfBirth, String email, String password, Address address, Pharmacy pharmacy)
    {
        super(name, surname, dateOfBirth, email, password, address);
        //this.pharmacy = pharmacy;
    }

    public PharmacyAdmin(long id, String name, String surname, LocalDate dateOfBirth, String email, String password, Address address, Pharmacy pharmacy)
    {
        super(id, name, surname, dateOfBirth, email, password, address);
        //this.pharmacy = pharmacy;
    }
}
