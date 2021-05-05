package com.schnabel.schnabel.users.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.schnabel.schnabel.misc.model.Address;

import lombok.*;

/**
 * Patient user
 */
@Entity
@Table(name = "patients")
@Getter
@Setter
@EqualsAndHashCode
public class Patient extends User
{
    public Patient()
    {

    }

    public Patient(String name, String surname, LocalDate dateOfBirth, String email, String password, Address address)
    {
        super(name, surname, dateOfBirth, email, password, address);
    }

    public Patient(Long id, String name, String surname, LocalDate dateOfBirth, String email, String password, Address address)
    {
        super(id, name, surname, dateOfBirth, email, password, address);
    }
}
