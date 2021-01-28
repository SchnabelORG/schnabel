package com.schnabel.schnabel.misc.model;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class Address
{
    private String postcode;
    private String city;
    private String street;
    private int streetNo;

    public Address()
    {
        this.postcode = "Placeholderpostcode";
        this.city = "Placeholdercity";
        this.street = "Placeholderstreet";
        this.streetNo = 1;
    }

    public Address(String postcode, String city, String street, int streetNo)
    {
        this.postcode = postcode;
        this.city = city;
        this.street = street;
        this.streetNo = streetNo;
    }
}
