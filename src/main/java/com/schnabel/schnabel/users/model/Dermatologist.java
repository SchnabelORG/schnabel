package com.schnabel.schnabel.users.model;

import java.util.List;

import javax.persistence.*;

import com.schnabel.schnabel.misc.model.Address;
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
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "dermatologist_pharmacy",
    joinColumns = @JoinColumn(name = "dermatologist_id"),
    inverseJoinColumns = @JoinColumn(name = "pharmacy_id"))
    private List<Pharmacy> pharmacies;

    public Dermatologist(String name, String surname, String email, String encodedPassword, Address address, boolean b) {
        super(name, surname, email, encodedPassword, address, b);
    }

}
