package com.schnabel.schnabel.email.service;

import java.util.Optional;

import javax.mail.internet.MimeMessage;

import com.schnabel.schnabel.email.model.Activation;
import com.schnabel.schnabel.email.model.Mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService implements IMailService {

    private final JavaMailSender javaMailSender;
    private final IActivationService activationService;
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);
    private static final String MAIL_FROM = "schnabel.isaproj@gmail.com";
    @Value("${custom.addr}")
    private String ADDR;
    @Value("${custom.fport}")
    private String PORT;

    @Autowired
    public MailService(JavaMailSender javaMailSender, IActivationService activationService) {
        this.javaMailSender = javaMailSender;
        this.activationService = activationService;
    }

    @Override
    public boolean sendMail(Mail mail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, true);

            mimeHelper.setSubject(mail.getMailSubject());
            mimeHelper.setFrom(mail.getMailFrom());
            mimeHelper.setTo(mail.getMailTo());
            mimeHelper.setText(mail.getMailContent());
            javaMailSender.send(mimeHelper.getMimeMessage());
            return true;
        } catch (Exception e) {
            logger.error("Exception while sending mail: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean sendActivationEmail(String email) {
        Mail mail = new Mail();
        mail.setMailFrom(MAIL_FROM);
        mail.setMailTo(email);
        mail.setMailSubject("Account activation");
        StringBuilder sb = new StringBuilder();
        sb.append("Greetings from The Schnabel Team,/n/nIn order to activate your account, go to the following address:\n");
        Activation activation = new Activation(email);
        if(activationService.add(activation).isPresent()) {
            sb.append(getActivationLink(email, activation.getId()));
            sb.append("\n\nKind regards,\nSchnabel Team");
            mail.setMailContent(sb.toString());
            return sendMail(mail);
        }
        return false;
    }

    private String getActivationLink(String email, String token) {
        StringBuilder sb = new StringBuilder();
        sb.append(ADDR);
        sb.append(":");
        sb.append(PORT);
        sb.append("/email/activate/");
        sb.append(token);
        return sb.toString();
    }

    @Override
    public String activate(String token) {
        Optional<Activation> activation = activationService.get(token);
        if (!activation.isPresent()) {
            return "";
        }

        return activationService.remove(token) ?
            activation.get().getEmail()
            : "";
    }
    
}
