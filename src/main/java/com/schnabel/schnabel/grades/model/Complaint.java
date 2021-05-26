package com.schnabel.schnabel.grades.model;


import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.users.model.Patient;
import lombok.*;

import javax.persistence.*;

/**
 * Complaint
 */

@Entity
@Table(name = "complaints")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Complaint implements IIdentifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_patient_id")
    private Patient patient;

    @Column
    private String complaintText;

    @Column
    private String response;


}
