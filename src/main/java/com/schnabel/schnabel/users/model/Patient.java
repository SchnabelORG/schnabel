package com.schnabel.schnabel.users.model;

import javax.persistence.*;

import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.misc.model.IIdentifiable;

import com.schnabel.schnabel.users.DTO.UserDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "patients")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Patient implements IIdentifiable<Integer>
{
    // TODO(Jovan): Use UUID?
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String surname;
    @Setter(AccessLevel.PROTECTED)
    private String email;
    @Embedded
    private Address address;
    private boolean enabled;
    private String password;

    public Patient(UserDTO userDTO) {
        super();
        this.name = userDTO.getName();
        this.surname = userDTO.getSurname();
        this.email = userDTO.getEmail();
        this.address = new Address(userDTO.getAddress());
        this.password = userDTO.getPassword();
        this.enabled = false;
    }

    @Override
    public Integer getId()
    {
        return this.id;
    }
}
