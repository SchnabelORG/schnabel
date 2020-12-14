package com.schnabel.schnabel.drugs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "drugs")
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

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null) return false;
        if(this.getClass() != o.getClass()) return false;
        final Drug drug = (Drug) o;
        if(this.id != drug.getId()) return false;
        if(!this.name.equals(drug.getName())) return false;
        if(!this.description.equals(drug.getDescription())) return false;
        return true;
    }
}
