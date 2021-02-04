package com.schnabel.schnabel.specialoffer.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;

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
public class SpecialOffer implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String content;
    @Embedded
    private Period validPeriod;
    @OneToOne(fetch = FetchType.LAZY)
    private Pharmacy pharmacy;

    public boolean isValidPeriod(LocalDate from, LocalDate until)
    {
        return this.validPeriod.getStartTime().compareTo(from) >= 0
            || this.validPeriod.getEndTime().compareTo(until) <= 0;
    }

    @Override
    public String toString()
    {
        return this.name + " " + this.content 
            + " valid from: " 
            + this.validPeriod.getStartTime().toString() 
            + " to: " + this.validPeriod.getEndTime().toString();
    }
}
