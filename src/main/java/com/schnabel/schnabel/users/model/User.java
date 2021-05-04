package com.schnabel.schnabel.users.model;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.misc.model.Address;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User implements IIdentifiable<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(nullable = false)
    protected String name;
    @Column(nullable = false)
    protected String surname;
    @Column(nullable = false)
    protected LocalDate dateOfBirth;
    @Column(nullable = false)
    protected String email;
    @Column(nullable = false)
    protected String password;
    @Embedded
    protected Address address;

    public User(String name, String surname, LocalDate dateOfBirth, String email, String password, Address address)
    {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public User(long id, String name, String surname, LocalDate dateOfBirth, String email, String password, Address address)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.address = address;
    }
}
