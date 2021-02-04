package com.schnabel.schnabel.pharmacies.model;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "term")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Term implements IIdentifiable<Integer> {

    @Id
    private int id;
    private LocalDateTime termBeginning;
    private LocalDateTime termEnd;
    private int pharmacyId;
    private int employeeId;
    private int patientId;


    @Override
    public Integer getId() {
        return this.id;
    }
}
