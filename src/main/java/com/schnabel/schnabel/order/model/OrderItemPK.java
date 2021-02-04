package com.schnabel.schnabel.order.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderItemPK implements Serializable
{
    private static final long serialVersionUID = 6738973711086684835L;
    @Column(name = "drug_id")
    private int drugId;
    @Column(name = "order_id")
    private int orderId;
}  
