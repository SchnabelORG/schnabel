package com.schnabel.schnabel.users.model;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

import lombok.*;

/**
 * System admin user
 */
@Entity
@DiscriminatorValue("SystemAdmin")
@Getter
@Setter
@EqualsAndHashCode
public class SystemAdmin extends User
{

}
