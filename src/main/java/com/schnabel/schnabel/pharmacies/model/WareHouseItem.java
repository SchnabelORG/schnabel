package com.schnabel.schnabel.pharmacies.model;

import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.model.DrugPrice;
import com.schnabel.schnabel.misc.model.IIdentifiable;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

/**
 * Warehouse item
 */
@Entity
@Table(name = "warehouseitem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WareHouseItem implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int quantity;
    @Column
    private int available;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id")
    private Drug drug;

    @OneToMany(mappedBy = "wareHouseItem", fetch = FetchType.LAZY)
    private List<DrugPrice> drugPrices;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pharmacy_id")
    private Pharmacy pharmacy;

    public WareHouseItem(Drug drug, Pharmacy pharmacy)
    {   
        this.drug = drug;
        this.pharmacy = pharmacy;
    }

    public DrugPrice getPriceForToday()
    {
        if(this.drugPrices.isEmpty())
        {
            return null;
        }
        for(DrugPrice drugPrice : this.drugPrices)
        {
           if((drugPrice.getPriceStartDate().isBefore(LocalDate.now()) || drugPrice.getPriceStartDate().isEqual(LocalDate.now())) && (drugPrice.getPriceEndDate().isAfter(LocalDate.now()) || drugPrice.getPriceEndDate().isEqual(LocalDate.now())))
           {
               return drugPrice;
           }
        }
        return null;
     }

     public boolean addPrice(DrugPrice drugPrice)
     {
        if(drugPrices.isEmpty())
        {
            this.drugPrices.add(drugPrice);
            return true;
        }
        for(DrugPrice dPrice : drugPrices)
        {
           if(dPrice.getPriceStartDate().isBefore(drugPrice.getPriceEndDate()) && drugPrice.getPriceStartDate().isBefore(dPrice.getPriceEndDate()))
           {
               return false;
           }
        }
        this.drugPrices.add(drugPrice);
        return true;
     }
}
