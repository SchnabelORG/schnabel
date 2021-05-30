package com.schnabel.schnabel.users.model;


import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.misc.model.IIdentifiable;
import lombok.*;

import javax.persistence.*;

/**
 * Drug Allergy
 */

@Entity
@Table(name = "allergies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Allergy implements IIdentifiable<Long> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO(Radovan): what is allergyType?!?!? Is it necessary?
    @Column
    private String allergyType;

    @ManyToOne
    @JoinColumn(name = "allergy_drug_id")
    private Drug drug;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;


}
