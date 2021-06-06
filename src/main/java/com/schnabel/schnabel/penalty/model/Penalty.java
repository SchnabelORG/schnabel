package com.schnabel.schnabel.penalty.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schnabel.schnabel.misc.UnixToLocalDateTimeConverter;
import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.users.model.Patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Penalty
 */
@Entity
@Table(name = "penalties")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Penalty implements IIdentifiable<Long> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @JsonDeserialize(using = UnixToLocalDateTimeConverter.class)
    private LocalDateTime date;
    private EPenaltyReason reason;

    public Penalty(Patient patient, LocalDateTime date, EPenaltyReason reason) {
        this.patient = patient;
        this.date = date;
        this.reason = reason;
    }
}
