package com.schnabel.schnabel.users.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.schnabel.schnabel.misc.model.Address;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Patient user
 */
@Data
@Entity
@Builder
@Table(name = "patients")
@NoArgsConstructor
public class Patient extends User
{
    public Patient(String name, String surname, LocalDate dateOfBirth, String email, String password, Address address)
    {
        super(name, surname, dateOfBirth, email, password, address);
    }
}
