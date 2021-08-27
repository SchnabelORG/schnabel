package com.schnabel.schnabel.users.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import java.time.LocalDate;
import com.schnabel.schnabel.misc.model.Address;

import lombok.*;

/**
 * Supplier user
 */
@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Supplier extends User
{
    @Column(nullable = false)
    private String firm;

    public Supplier(String name, String surname, String email, String password, Address address, boolean isActivated, String firm) {
        super(name, surname, email, password, address, isActivated);
        this.firm = firm;
    }

//    public Supplier(Long id, String name, String surname, LocalDate dateOfBirth, String email, String password, Address address, String company)
//    {
//        super();
//        this.company = company;
//    }
//
//    public Supplier(String name, String surname, LocalDate dateOfBirth, String email, String password, Address address, String company)
//    {
//        super();
//        this.company = company;
//    }
}
