package com.schnabel.schnabel.drugs.model;

import lombok.*;

import javax.persistence.*;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;

import java.time.LocalDate;

@Entity
@Table(name = "drugprice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DrugPrice implements IIdentifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double price;

    @Column(name = "price_start_date", nullable = false)
    private LocalDate priceStartDate;

    @Column(name = "price_end_date", nullable = true)
    private LocalDate priceEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouseitem_id")
    private WareHouseItem wareHouseItem;

    public DrugPrice(double price, LocalDate priceStartDate, LocalDate priceEndDate, WareHouseItem wareHouseItem) 
    {
        this.price = price;
        this.priceStartDate = priceStartDate;
        this.priceEndDate = priceEndDate;
        this.wareHouseItem = wareHouseItem;
    }
}
