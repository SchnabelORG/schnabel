package com.schnabel.schnabel.procurement.model;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.users.model.Supplier;

import lombok.*;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Offer implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private int price;
    
    @Column(nullable = false)
    private LocalDate dateOfDelivery;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}
