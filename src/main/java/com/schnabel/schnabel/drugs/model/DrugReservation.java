package com.schnabel.schnabel.drugs.model;


import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.pharmacies.model.WareHouse;
import com.schnabel.schnabel.users.model.Patient;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Drug Reservation
 */
@Entity
@Table(name = "drugs_reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DrugReservation implements IIdentifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int quantity;

    @Column(name = "reservation_date")
    private LocalDateTime reservationDate;

    @Column(name = "end_reservation")
    private LocalDateTime endOfReservation;

    @ManyToOne
    @JoinColumn(name = "reservation_patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "reserved_drug_id")
    private Drug drug;

    @ManyToOne
    @JoinColumn(name = "wareh_id")
    private WareHouse wareHouse;

}
