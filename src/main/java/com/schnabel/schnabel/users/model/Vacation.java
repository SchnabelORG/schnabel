package com.schnabel.schnabel.users.model;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "vacations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Vacation implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Period period;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    @ManyToOne
    @JoinColumn(name = "medical_employee_id")
    private MedicalEmployee medicalEmployee;

    @Enumerated(EnumType.STRING)
    @Column
    private VacationStatus vacationStatus;

    @Column
    private String reasonOfRejection;
}
