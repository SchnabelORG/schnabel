package com.schnabel.schnabel.pharmacies.model;

import java.util.List;

import javax.persistence.*;

import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.model.Pharmacist;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Pharmacy
 */
@Entity
@Table(name = "pharmacies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Pharmacy implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Embedded
    private Address address;

    @OneToOne(mappedBy = "pharmacy")
    private WareHouse wareHouse;

    @ManyToMany(mappedBy = "pharmacies")
    private List<Dermatologist> dermatologists;

    @OneToMany(mappedBy = "pharmacy")
    private List<Pharmacist> pharmacists;

    public Pharmacy(Long id, String name, Address address)
    {
        this.id = id;
        this.address = address;
        this.name = name;
    }
}
