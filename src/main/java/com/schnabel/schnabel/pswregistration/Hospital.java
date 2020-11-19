package com.schnabel.schnabel.pswregistration;

public class Hospital {
    private String apiKey;
    private String id;

    public Hospital()
    {
        this.apiKey = "placeholderapi";
        this.id = "placeholderid";
    }

    public Hospital(String id, String api)
    {
        this.id = id;
        this.apiKey = api;
    }

    public String getApiKey()
    {
        return this.apiKey;
    }

    public void setApiKey(String api)
    {
        this.apiKey = api;
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
