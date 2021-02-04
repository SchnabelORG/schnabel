package com.schnabel.schnabel.users.model;

import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.misc.model.IIdentifiable;
import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employeduser")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EmployedUser implements IIdentifiable<Integer>{
    @Id
    private int id;
    private String name;
    private String surname;
    @Setter(AccessLevel.PROTECTED)
    private String email;
    private String password;
    @Embedded
    private Address address;
    private EmployedUserType userType;

    @Override
    public Integer getId()
        {
            return this.id;
        }


}
