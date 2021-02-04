package com.schnabel.schnabel.pharmacies.model;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

@Entity
@Table(name = "worksin")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class WorksIn implements IIdentifiable<Integer> {

    @Id
    private int id;
    private int pharmacyId;
    private int employeeId;
    private LocalTime startOfShift;
    private LocalTime endOfShift;


    @Override
    public Integer getId()
    {
        return this.id;
    }

}
