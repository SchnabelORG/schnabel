package com.schnabel.schnabel.drugreservations.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.users.model.Patient;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Used for tracking drug reservations
 * 
 */
@Entity
@Table(name = "drugreservations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class DrugReservation implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id")
    private Drug drug;
    @Column(name = "valid_until")
    private LocalDate validUntil;

    /**
     * Checks whether the reservation is still valid
     * compared to the date parameter
     * @param date used for checking validity
     * @return true if <b>date</b> is before reservation date
     */
    public boolean isValid(LocalDate date)
    {
        return this.validUntil.compareTo(date) >= 0;
    }
}
