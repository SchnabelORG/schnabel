package com.schnabel.schnabel.procurement.model;

import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.misc.model.IIdentifiable;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orderitems")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderItem implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private int quantity;
    
    @ManyToOne
    @JoinColumn(name = "drug_id")
    private Drug drug;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(int quantity, Drug drug, Order order)
    {
        this.quantity = quantity;
        this.drug = drug;
        this.order = order;
    }
}
