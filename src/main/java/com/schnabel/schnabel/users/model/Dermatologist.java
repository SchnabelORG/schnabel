package com.schnabel.schnabel.users.model;

import java.util.List;

import javax.persistence.*;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import lombok.*;

/**
 * Dermatologist user
 */
@Entity
@Table(name = "dermatologists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Dermatologist extends MedicalEmployee
{
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "dermatologist_pharmacy",
        joinColumns = @JoinColumn(name = "dermatologist_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "pharmacy_id",
        referencedColumnName = "id")) 
    private List<Pharmacy> pharmacies;
}
