package com.schnabel.schnabel.pharmacies.model;


import com.schnabel.schnabel.drugs.model.DrugPrice;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "warehouseitem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WareHouseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int quantity;
    @Column
    private int available;


    @ManyToOne
    @JoinColumn(name = "drug_id")
    private DrugPrice drugPrice;
}
