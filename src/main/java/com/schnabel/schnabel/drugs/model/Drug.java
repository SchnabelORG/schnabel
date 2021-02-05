package com.schnabel.schnabel.drugs.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.misc.model.IIdentifiable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Medical drug
 */
@Entity
@Table(name = "drugs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Drug implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; 
    @Column(nullable = false)
    private String name;
    @Column(nullable = false) 
    private String description;
    @Column(nullable = false)
    private int quantity; 
    @Column(nullable = false)
    private double price;
    @Embedded
    private Period priceDuration;

}
