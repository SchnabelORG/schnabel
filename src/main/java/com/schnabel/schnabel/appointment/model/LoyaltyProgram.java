package com.schnabel.schnabel.appointment.model;
import com.schnabel.schnabel.misc.model.IIdentifiable;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "loyalty")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class LoyaltyProgram implements IIdentifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double pharmacyPoints;

    @Column
    private double dermatologistPoints;

    @Column
    private double pointsForBronze;

    @Column
    private double bronzeDiscount;

    @Column
    private double pointsForSilver;

    @Column
    private double silverDiscount;

    @Column
    private double pointsForGold;

    @Column
    private double goldDiscount;

}
