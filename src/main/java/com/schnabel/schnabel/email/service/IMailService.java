package com.schnabel.schnabel.email.service;

import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.email.model.Mail;

public interface IMailService {
    boolean sendMail(Mail mail);
    boolean sendActivationEmail(String email);
    boolean sendAppointmentConfirmationMail(String email, Appointment appointment);
    String activate(String token);
}
