package com.schnabel.schnabel.appointment.model;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Appointment report
 */

@Entity
@Table(name = "app_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AppointmentReport implements IIdentifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;

    @OneToMany(mappedBy = "report")
    private List<RecommendedMed> recommendedMeds;

    @OneToOne
    @JoinColumn(name="appointment_id")
    private Appointment appointment;
}
