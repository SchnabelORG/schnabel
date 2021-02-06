package com.schnabel.schnabel.users.model;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Shift for employed user
 */
@Entity
@Table(name = "shifts")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Shift implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private EmployedUser employedUser;
    @ManyToOne
    private Pharmacy pharmacy;
    private LocalTime startTime;
    private LocalTime endTime;

    public Shift(EmployedUser employedUser, Pharmacy pharmacy, LocalTime startTime, LocalTime endTime)
    {
        this.employedUser = employedUser;
        this.pharmacy = pharmacy;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
