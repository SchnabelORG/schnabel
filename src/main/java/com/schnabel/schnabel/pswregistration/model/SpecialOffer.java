package com.schnabel.schnabel.pswregistration.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;


@Entity
@Table(name = "specialoffers")
@EqualsAndHashCode
public class SpecialOffer
{
    @Id
    private int id;
    private String name;
    private String content;
    // TODO(Jovan): Make DateRange class that works with PostgreSQL?
    private LocalDate validFrom;
    private LocalDate validUntil;
    private String pharmacyId;


    public SpecialOffer()
    {
        this.id = 1;
        this.name = "placeholdername";
        this.content = "placeholdercontent";
        this.validFrom = LocalDate.now();
        this.validUntil = LocalDate.now();
        this.pharmacyId = "Jankovic";
    }

    public SpecialOffer(int id, String name, String content, LocalDate validFrom, LocalDate validUntil, String pharmacyId)
    {
    
        this.id = id;
        this.name = name;
        this.content = content;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.pharmacyId = pharmacyId;

    }

    public boolean isValidPeriod(LocalDate from, LocalDate until)
    {
        return this.validFrom.compareTo(from) >= 0
            || this.validUntil.compareTo(until) <= 0;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return this.content;
    }

    public void setValidFrom(LocalDate validFrom)
    {
        this.validFrom = validFrom;
    }

    public LocalDate getValidFrom()
    {
        return validFrom;
    }

    public void setValidUntil(LocalDate validUntil)
    {
        this.validUntil = validUntil;
    }

    public LocalDate getValidUntil()
    {
        return validUntil;
    }

    public void setPharmacyId(String pharmacyId)
    {
        this.pharmacyId = pharmacyId;
    }

    public String getPharmacyId()
    {
        return this.pharmacyId;
    }
    @Override
    public String toString()
    {
        return this.name + " " + this.content 
            + " valid from: " 
            + this.validFrom.toString() 
            + " to: " + this.validUntil.toString();
    }
}
