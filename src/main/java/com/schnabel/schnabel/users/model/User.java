package com.schnabel.schnabel.users.model;

import com.schnabel.schnabel.misc.model.IIdentifiable;
import com.schnabel.schnabel.misc.model.Address;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.SequenceGenerator;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Inheritance(strategy=TABLE_PER_CLASS)
public abstract class User implements IIdentifiable<Long>
{
    @Id
    @SequenceGenerator(name = "seqGen", sequenceName = "seq", initialValue = 2, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String surname;
    
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Embedded
    private Address address;


    public User(String name, String surname, LocalDate dateOfBirth, String email, String password, Address address)
    {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.address = address;
    }
}
