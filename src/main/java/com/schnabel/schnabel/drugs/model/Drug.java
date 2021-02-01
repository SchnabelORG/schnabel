package com.schnabel.schnabel.drugs.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.misc.model.IIdentifiable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "drugs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Drug implements IIdentifiable<Integer>
{
    @Id
    private int id; 
    private String name; 
    private String description;
    private double price;
    @Embedded
    private Period priceDuration;

    @Override
    public Integer getId()
    {
        return this.id;
    }
}
