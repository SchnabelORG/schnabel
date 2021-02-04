package com.schnabel.schnabel.util;

import com.schnabel.schnabel.users.model.Patient;
import org.springframework.context.ApplicationEvent;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Patient patient;

    public OnRegistrationCompleteEvent(Patient patient, String appUrl) {
        super(patient);
        this.patient = patient;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Patient getUser() {
        return patient;
    }

}
