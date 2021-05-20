package com.schnabel.schnabel.misc.model;

import javax.persistence.Column;
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
    @Column(nullable = false)
    private String postcode;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String street;
    @Column(name = "street_no", nullable = false)
    private String streetNo;

    public Address()
    {
        this.postcode = "Placeholderpostcode";
        this.city = "Placeholdercity";
        this.street = "Placeholderstreet";
        this.streetNo = "1";
    }

    public Address(String postcode, String city, String street, String streetNo)
    {
        this.postcode = postcode;
        this.city = city;
        this.street = street;
        this.streetNo = streetNo;
    }
}
