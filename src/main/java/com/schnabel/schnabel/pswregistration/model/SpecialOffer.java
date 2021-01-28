package com.schnabel.schnabel.pswregistration.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "specialoffers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SpecialOffer
{
    @Id
    private int id;
    private String name;
    private String content;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private String pharmacyId;

    public boolean isValidPeriod(LocalDate from, LocalDate until)
    {
        return this.validFrom.compareTo(from) >= 0
            || this.validUntil.compareTo(until) <= 0;
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
