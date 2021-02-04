package com.schnabel.schnabel.users.model;


import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "systemadmins")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SystemAdmin implements IIdentifiable<Integer> {

    @Id
    private int id;
    private String name;
    private String surname;
    @Setter(AccessLevel.PROTECTED)
    private String email;
    @Embedded
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    private Pharmacy pharmacy;


    @Override
    public Integer getId() {
        return this.id;
    }
}
