package com.schnabel.schnabel.promotion.model;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "promotions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Promotion implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Embedded
    private Period period;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    public Promotion(String description, Period period, Pharmacy pharmacy) 
    {   
        this.description = description;
        this.period = period;
        this.pharmacy = pharmacy;
    }
}
