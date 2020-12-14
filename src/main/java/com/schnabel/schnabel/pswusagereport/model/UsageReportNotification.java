package com.schnabel.schnabel.pswusagereport.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usagereportnotifications")
public class UsageReportNotification
{

    @Id
    private int _id;
    private String _endpoint;
    private String _message;
    private String _filename;

    public UsageReportNotification()
    {
        _id = 1;
        _endpoint = "placeholderendpoint";
        _message = "placeholdermessage";
        _filename = "placeholderfilename";
    }

    public UsageReportNotification(String endpoint, String message, String filename)
    {
        _endpoint = endpoint;
        _message = message;
        _filename = filename;
    }

    public int getId()
    {
        return _id;
    }

    public void setId(int id)
    {
        _id = id;
    }

    public String getEndpoint()
    {
        return _endpoint;
    }

    public void setEndpoint(String endpoint)
    {
        _endpoint = endpoint;
    }

    public String getMessage()
    {
        return _message;
    }

    public void setMessage(String message)
    {
        _message = message;
    }

    public String getFilename()
    {
        return _filename;
    }

    public void setFilename(String filename)
    {
        _filename = filename;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null) return false;
        if(this.getClass() != o.getClass()) return false;
        UsageReportNotification notification = (UsageReportNotification) o;
        if(_id != notification.getId()) return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Endpoint: " + _endpoint + " Message: " + _message + " Filename: " + _filename;
    }
}
