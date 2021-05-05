package com.schnabel.schnabel.users.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import lombok.*;

/**
 * Pharmacist user
 */
@Entity
@Table(name = "pharmacists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Pharmacist extends User
{
    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;
}
