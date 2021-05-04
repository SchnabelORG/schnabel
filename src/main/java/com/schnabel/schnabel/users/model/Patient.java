package com.schnabel.schnabel.users.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.*;

/**
 * Patient user
 */
@Entity
@DiscriminatorValue("Patient")
@Getter
@Setter
@EqualsAndHashCode
public class Patient extends User
{
    
}
