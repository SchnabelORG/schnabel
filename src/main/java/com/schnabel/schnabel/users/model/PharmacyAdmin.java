package com.schnabel.schnabel.users.model;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.misc.model.Address;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pharmacyadmins")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyAdmin implements IIdentifiable<Integer>
{
    @Id
    private int id;
    private String name;
    private String surname;
    @Setter(AccessLevel.PROTECTED)
    private String email;
    @Embedded
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    private Pharmacy pharmacy;


    @Override
    public Integer getId()
    {
        return this.id;
    }
}
