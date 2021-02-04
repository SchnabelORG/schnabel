package com.schnabel.schnabel.order.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.schnabel.schnabel.drugs.model.Drug;

import org.hibernate.annotations.ManyToAny;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderItem 
{
    @EmbeddedId
    private OrderItemPK id;

    @ManyToOne
    @MapsId("drugId")
    @JoinColumn(name = "drug_id", insertable = false, updatable = false)
    private Drug drug;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    private int quantity;

    public OrderItem(Drug drug, Order order, int quantity)
    {
        this.drug = drug;
        this.order = order;
        this.quantity = quantity;
    }
}
