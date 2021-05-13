package com.schnabel.schnabel.users.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends User
{
    @OneToMany(mappedBy = "patient")
    private List<Allergy> allergies;

}
