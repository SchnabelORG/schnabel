package com.schnabel.schnabel.order.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.schnabel.schnabel.misc.model.IIdentifiable;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Order implements IIdentifiable<Integer> 
{
    @Id
    private int id;
    private String description;
    @Embedded
    private List<OrderItem> orderItems;
    private LocalDate deadline;

    @Override
    public Integer getId()
    {
        return this.id;
    }

}
