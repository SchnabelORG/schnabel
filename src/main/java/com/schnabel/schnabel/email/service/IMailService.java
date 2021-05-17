package com.schnabel.schnabel.email.service;

import com.schnabel.schnabel.email.model.Mail;

public interface IMailService {
    boolean sendMail(Mail mail);
    boolean sendActivationEmail(String email);
    String activate(String token);
}
