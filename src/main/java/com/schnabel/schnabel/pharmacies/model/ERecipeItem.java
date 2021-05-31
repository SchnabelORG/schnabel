package com.schnabel.schnabel.pharmacies.model;


import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.misc.model.IIdentifiable;
import lombok.*;

import javax.persistence.*;

/**
 * eRecipe item
 */

@Entity
@Table(name = "recipe_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ERecipeItem  implements IIdentifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "recipe_drug_id")
    private Drug drug;

}
