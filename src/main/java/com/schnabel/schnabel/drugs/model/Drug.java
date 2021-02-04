package com.schnabel.schnabel.drugs.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.order.model.OrderItem;
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
    private int quantity;
    private double price;
    @Embedded
    private Period priceDuration;

    // TODO(Kris): Use initialized final field for bypassing lombok AllArgsConstructor?
    @OneToMany(mappedBy = "drug")
    private final Set<OrderItem> orderItems = new HashSet<OrderItem>();

    @Override
    public Integer getId()
    {
        return this.id;
    }
}
