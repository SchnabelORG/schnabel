package com.schnabel.schnabel.users.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.schnabel.schnabel.misc.model.Address;
import lombok.*;

/**
 * System admin user
 */
@Table(name = "systemadmins")
@EqualsAndHashCode
@Data
@Entity
@Builder
public class SystemAdmin extends User
{
    public SystemAdmin()
    {
    }

    public SystemAdmin(String name, String surname, String email, String encodedPassword, Address address, boolean b) {
        super(name, surname, email, encodedPassword, address, b);
    }
}
