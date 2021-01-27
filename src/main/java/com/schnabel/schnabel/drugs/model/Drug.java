package com.schnabel.schnabel.drugs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;

@Entity
@Table(name = "drugs")
@EqualsAndHashCode
public class Drug {
    @Id
    private int id; 
    private String name; 
    private String description;

    public Drug()
    {
        this.id = 1;
        this.name = "placeholdername";
        this.description = "placeholderdescription";
    }

    public Drug(int id, String name, String description)
    {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
