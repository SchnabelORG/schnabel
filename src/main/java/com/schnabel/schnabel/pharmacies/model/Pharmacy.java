package com.schnabel.schnabel.pharmacies.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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

    @ManyToMany(mappedBy = "pharmacies", fetch = FetchType.LAZY)
    private List<Dermatologist> dermatologists;

    @OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY)
    private List<Pharmacist> pharmacists;

    @Min(0)
    @Max(5)
    private int score;

    public Pharmacy(Long id, String name, Address address, int score)
    {
        this.id = id;
        this.address = address;
        this.name = name;
        this.score = score;
    }
}
