package com.schnabel.schnabel.drugreservation.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
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

@Entity
@Table(name = "drugreservations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class DrugReservation implements IIdentifiable<Integer>
{
    @Id
    private int id;
    @OneToOne
    private Patient patient;
    @OneToOne
    private Drug drug;
    private LocalDate validUntil;

    @Override
    public Integer getId()
    {
        return this.id;
    }
}
