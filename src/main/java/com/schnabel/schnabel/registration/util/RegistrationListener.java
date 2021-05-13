package com.schnabel.schnabel.registration.util;

import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private IPatientService patientService;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        Patient patient = event.getUser();
        String token = UUID.randomUUID().toString();
        patientService.createVerificationToken(patient, token);

        String recipientAddress = patient.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = event.getAppUrl() + "/api/patient/registrationConfirm/" + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("${mail.username}");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        String message = "This is the confirmation link to finish your registration.";
        email.setText(message + "\r\n" + confirmationUrl);
        mailSender.send(email);
    }
}
