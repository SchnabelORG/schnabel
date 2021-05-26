package com.schnabel.schnabel.drugs.model;

import javax.persistence.*;

import com.schnabel.schnabel.drugs.model.enums.DrugState;
import com.schnabel.schnabel.drugs.model.enums.DrugOrigin;
import com.schnabel.schnabel.drugs.model.enums.DrugType;
import com.schnabel.schnabel.drugs.model.enums.IssuingType;
import com.schnabel.schnabel.misc.model.IIdentifiable;

import lombok.*;

import java.util.List;

/**
 * Medical drug
 */
@Data
@Entity
@Builder
@Table(name = "drugs")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Drug implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String code;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false) 
    private String description;
    @Column
    @Enumerated(EnumType.STRING)
    private DrugType drugType;
    @Column
    @Enumerated(EnumType.STRING)
    private DrugState drugState;
    @Column
    @Enumerated(EnumType.STRING)
    private DrugOrigin drugOrigin;
    @Column
    private String producer;
    @Column
    private String dosage;
    @Column
    @Enumerated(EnumType.STRING)
    private IssuingType issuingType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "substitute_drugs",
            joinColumns = @JoinColumn(name = "drug_id"),
            inverseJoinColumns = @JoinColumn(name = "substitute_id")
    )
    private List<Drug> substituteDrugs;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "drug_ingredient",
            joinColumns = @JoinColumn(name = "drug_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;

    public Drug(String code, String name, String description, DrugType drugType, DrugState drugState, DrugOrigin drugOrigin, String producer, String dosage, IssuingType issuingType) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.drugType = drugType;
        this.drugState = drugState;
        this.drugOrigin = drugOrigin;
        this.producer = producer;
        this.dosage = dosage;
        this.issuingType = issuingType;
    }
}
