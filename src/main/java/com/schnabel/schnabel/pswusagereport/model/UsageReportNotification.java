package com.schnabel.schnabel.pswusagereport.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.schnabel.schnabel.misc.model.IIdentifiable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usagereportnotifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UsageReportNotification implements IIdentifiable<String>
{

    @Id
    private String filename;
    private String endpoint;
    private String message;

    @Override
    public String getId()
    {
        return this.filename;
    }

    @Override
    public String toString()
    {
        return "Endpoint: " + this.endpoint + " Message: " + this.message + " Filename: " + this.filename;
    }
}
