package com.schnabel.schnabel.users.model;

import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.misc.model.IIdentifiable;
import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "suppliers")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Supplier implements IIdentifiable<Long>
{
    @Id
    private long id;
    private String name;
    private String email;
    @Embedded
    private Address address;


    @Override
    public Long getId() {
        return this.id;
    }
}
