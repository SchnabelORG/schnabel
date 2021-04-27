package com.schnabel.schnabel.users.model;

import com.schnabel.schnabel.misc.model.Address;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Column;

import lombok.*;

import java.time.LocalDate;

/**
 * Supplier user
 */
@Entity
@DiscriminatorValue("Supplier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Supplier extends User
{
    @Column(nullable = false)
    private String firm;

    public Supplier(String name, String surname, LocalDate dateOfBirth, String email, String password, Address address, String firm)
    {
        super(name, surname, dateOfBirth, email, password, address);
        this.firm = firm;
    }

    public Supplier(long id, String name, String surname, LocalDate dateOfBirth, String email, String password, Address address, String firm)
    {
        super(id, name, surname, dateOfBirth, email, password, address);
        this.firm = firm;
    }
}