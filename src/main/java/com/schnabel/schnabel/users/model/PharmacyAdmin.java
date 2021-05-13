package com.schnabel.schnabel.users.model;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;

/**
 * Pharmacy admin user
 */
@Entity
@Table(name = "pharmacyadmins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PharmacyAdmin extends User
{
    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

}