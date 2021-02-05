package com.schnabel.schnabel.users.model;

import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.misc.model.IIdentifiable;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class EmployedUser implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    @Setter(AccessLevel.PROTECTED)
    private String email;
    @Column(nullable = false)
    private String password;
    @Embedded
    private Address address;

    public EmployedUser(String name, String surname, String email, String password, Address address)
    {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.address = address;
    }

}
