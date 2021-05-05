package com.schnabel.schnabel.procurement.model;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.users.model.PharmacyAdmin;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Order implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDate deadline;
    
    @Column
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "pharmacyadmin_id")
    private PharmacyAdmin pharmacyAdmin;
    
    @OneToMany(mappedBy = "order")
    private List<Offer> offers;
    
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
