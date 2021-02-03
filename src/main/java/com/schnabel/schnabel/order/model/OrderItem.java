package com.schnabel.schnabel.order.model;

import javax.persistence.OneToOne;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;

import com.schnabel.schnabel.drugs.model.Drug;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class OrderItem 
{
    @OneToOne(cascade = CascadeType.ALL)
    private Drug drug;
    private int quantity;

    public OrderItem(Drug drug, int quantity) 
    {
        this.drug = drug;
        this.quantity = quantity;
    }

}
