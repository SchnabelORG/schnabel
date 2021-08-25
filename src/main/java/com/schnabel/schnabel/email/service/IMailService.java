package com.schnabel.schnabel.email.service;

import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.email.model.Mail;

public interface IMailService {
    boolean sendMail(Mail mail);
    boolean sendActivationEmail(String email);
    boolean sendAppointmentConfirmationMail(String email, Appointment appointment);
    String activate(String token);
    boolean sendOrderClosingMail(String email, String content);
    boolean sendVacationMedicalEmployee(String email, String content);
    boolean sendNewPromotion(String email, String content);
    boolean sendReservationConfirmation(String email, String content);
}
