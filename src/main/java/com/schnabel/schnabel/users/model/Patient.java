package com.schnabel.schnabel.users.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.schnabel.schnabel.misc.model.Address;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class Patient extends User
{
    private boolean isActivated;
    private String phoneNo;
    public Patient(String name, String surname, String email, String password, Address address, boolean isActivated, String phoneNo)
    {
        super(name, surname, email, password, address);
        this.isActivated = isActivated;
        this.phoneNo = phoneNo;
    }
}
