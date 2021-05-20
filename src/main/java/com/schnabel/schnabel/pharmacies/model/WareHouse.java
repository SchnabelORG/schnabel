package com.schnabel.schnabel.pharmacies.model;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Pharmacy Warehouse
 */

@Entity
@Table(name = "warehouse")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WareHouse implements IIdentifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id", nullable = false)
    private List<WareHouseItem> wareHouseItems;

    @OneToOne
    @JoinColumn(name="pharmacy_id")
    private Pharmacy pharmacy;

}
