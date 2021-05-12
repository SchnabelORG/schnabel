package com.schnabel.schnabel.users.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.*;

/**
 * System admin user
 */
@Entity
@Table(name = "systemadmins")
@Getter
@Setter
@EqualsAndHashCode
public class SystemAdmin extends User
{

}
