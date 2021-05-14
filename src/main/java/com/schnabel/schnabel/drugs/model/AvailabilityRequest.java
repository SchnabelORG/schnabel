package com.schnabel.schnabel.drugs.model;


import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.model.Pharmacist;
import lombok.*;

import javax.persistence.*;

/**
 * Availagility Request
 */

@Entity
@Table(name = "availability_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AvailabilityRequest implements IIdentifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "request_pharmacy_id")
    private Pharmacy pharmacy;

    @ManyToOne
    @JoinColumn(name = "request_pharmacist_id")
    private Pharmacist pharmacist;

    @ManyToOne
    @JoinColumn(name = "request_drug_id")
    private Drug drug;

}
