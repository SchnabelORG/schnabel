package com.schnabel.schnabel.users.model;

import javax.persistence.*;

import com.schnabel.schnabel.misc.model.Address;

import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MedicalEmployee extends User
{
    @OneToMany(mappedBy = "medicalEmployee")
    private List<Vacation> vacations;

    @OneToMany(mappedBy = "medicalEmployee")
    private List<Shift> shifts;

    public MedicalEmployee(String name, String surname, String email, String password, Address address) {
        super(name, surname, email, password, address);
    }
}