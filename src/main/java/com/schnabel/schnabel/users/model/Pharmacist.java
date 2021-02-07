package com.schnabel.schnabel.users.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Pharmacist user
 */
@Entity
@DiscriminatorValue("Pharmacist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Pharmacist extends EmployedUser
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private Pharmacy pharmacy;

    public Pharmacist(String name, String surname, String email, String password, Address address, Pharmacy pharmacy)
    {
        super(name, surname, email, password, address);
        this.pharmacy = pharmacy;
    }

    public Pharmacist(long id, String name, String surname, String email, String password, Address address, Pharmacy pharmacy)
    {
        super(id, name, surname, email, password, address);
        this.pharmacy = pharmacy;
    }
}
