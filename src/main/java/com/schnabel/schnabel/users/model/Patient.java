package com.schnabel.schnabel.users.model;

import java.util.List;

import javax.persistence.*;

import com.schnabel.schnabel.misc.model.Address;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Patient user
 */
@Data
@Entity
@Builder
@Table(name = "patients")
@AllArgsConstructor
@NoArgsConstructor
public class Patient extends User
{
    private String phoneNo;
    @OneToMany(mappedBy = "patient")
    private List<Allergy> allergies;

    @ManyToMany( fetch = FetchType.LAZY)
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "pharmacy_id")
    )
    private List<Pharmacy> subscriptions;

    public Patient(String name, String surname, String email, String password, Address address, boolean isActivated, String phoneNo)
    {
        super(name, surname, email, password, address, isActivated);
        this.phoneNo = phoneNo;
    }
}
