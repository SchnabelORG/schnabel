package com.schnabel.schnabel.pswregistration.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;

@Entity
@Table(name = "hospitals")
@EqualsAndHashCode
public class Hospital {
    @Id
    private String apiKey;
    private String name;

    public Hospital()
    {
        this.apiKey = "placeholderapi";
        this.name = "placeholdername";
    }

    public Hospital(String name, String api)
    {
        this.name = name;
        this.apiKey = api;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getApiKey()
    {
        return this.apiKey;
    }

    public void setApiKey(String api)
    {
        this.apiKey = api;
    }
}
