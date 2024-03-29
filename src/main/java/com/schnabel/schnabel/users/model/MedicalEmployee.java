package com.schnabel.schnabel.users.model;

import javax.persistence.*;

import com.schnabel.schnabel.misc.model.Address;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Table(name = "medical_employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalEmployee extends User
{
    @OneToMany(mappedBy = "medicalEmployee")
    private List<Vacation> vacations;

    @OneToMany(mappedBy = "medicalEmployee")
    private List<Shift> shifts;

    @ColumnDefault("false")
    @Column(nullable = true)
    private boolean isDefaultPasswordChanged;

    private double score;
    public MedicalEmployee(String name, String surname, String email, String password, Address address) {
        super(name, surname, email, password, address);
    }
}