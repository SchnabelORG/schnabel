package com.schnabel.schnabel.users.model;

import javax.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "medical_employees")
@Getter
@Setter
public class MedicalEmployee extends User
{
    @OneToMany(mappedBy = "medicalEmployee")
    private List<Vacation> vacations;

    @OneToMany(mappedBy = "medicalEmployee")
    private List<Shift> shifts;

    private double score;
}