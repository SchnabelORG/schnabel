package com.schnabel.schnabel.users.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
    @ManyToOne
    @JoinColumn(name = "pharmacy_id", referencedColumnName = "id", nullable = true)
    private Pharmacy pharmacy;
}
