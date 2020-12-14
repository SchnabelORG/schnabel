package com.schnabel.schnabel.pswusagereport.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usagereportnotifications")
public class UsageReportNotification
{

    @Id
    private String filename;
    private String endpoint;
    private String message;

    public UsageReportNotification()
    {
        this.endpoint = "placeholderendpoint";
        this.message = "placeholdermessage";
        this.filename = "placeholderfilename";
    }

    public UsageReportNotification(String endpoint, String message, String filename)
    {
        this.endpoint = endpoint;
        this.message = message;
        this.filename = filename;
    }

    public String getEndpoint()
    {
        return this.endpoint;
    }

    public void setEndpoint(String endpoint)
    {
        this.endpoint = endpoint;
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getFilename()
    {
        return this.filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null) return false;
        if(this.getClass() != o.getClass()) return false;
        UsageReportNotification notification = (UsageReportNotification) o;
        if(this.filename != notification.getFilename()) return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Endpoint: " + this.endpoint + " Message: " + this.message + " Filename: " + this.filename;
    }
}
