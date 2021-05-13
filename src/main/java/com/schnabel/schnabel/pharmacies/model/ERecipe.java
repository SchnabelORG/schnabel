package com.schnabel.schnabel.pharmacies.model;


import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.model.Pharmacist;
import lombok.*;


import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "recipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ERecipe implements IIdentifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pat_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "recipe_pharmacist_id")
    private Pharmacist pharmacist;

    @ManyToOne
    @JoinColumn(name = "recipe_pharmacy_id")
    private Pharmacy Pharmacy;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", nullable = false)
    private List<ERecipeItem> recipeItems;

}
