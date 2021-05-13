package com.schnabel.schnabel.users.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.schnabel.schnabel.misc.model.Address;

import com.schnabel.schnabel.registration.dto.UserDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Patient user
 */
@Data
@Entity
@Table(name = "patients")
@NoArgsConstructor
public class Patient extends User
{

    @Column(nullable = false)
    private boolean isEnabled;

    public Patient(String name, String surname, LocalDate dateOfBirth, String email, String password, Address address)
    {
        super(name, surname, dateOfBirth, email, password, address);
    }

    public Patient(UserDTO userDTO) {
        super(userDTO.getName(), userDTO.getSurname(), userDTO.getDateOfBirth(),userDTO.getEmail(), userDTO.getPassword(), new Address(userDTO.getAddress()));
        this.isEnabled = false;
    }
}
