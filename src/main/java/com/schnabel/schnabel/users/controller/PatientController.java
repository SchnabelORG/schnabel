package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.misc.exceptions.PatientAlreadyExistsException;
import com.schnabel.schnabel.users.DTO.UserDTO;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.model.VerificationToken;
import com.schnabel.schnabel.users.service.IPatientService;

import com.schnabel.schnabel.util.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Calendar;

@RestController
public class PatientController
{
    private final IPatientService patientService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    public PatientController(IPatientService patientService)
    {
        this.patientService = patientService;
    }

    @GetMapping("/api/patient/{id}")
    public ResponseEntity<Patient> get(@PathVariable long id)
    {
        Patient patient = patientService.get(id);
        return patient == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(patient);
    }

    @GetMapping("/api/patient")
    public ResponseEntity<Iterable<Patient>> getAll()
    {
        return ResponseEntity.ok(patientService.getAll());
    }

    @PostMapping("/api/patient/registration")
    public ResponseEntity<String> registerPatientAccount(@RequestBody UserDTO userDTO) {
        try {
            Patient registered = patientService.registerNewAccount(userDTO);
            final String baseUrl =
                    ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            String appUrl = baseUrl;
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, appUrl));
        } catch (PatientAlreadyExistsException uaeEx) {
            return new ResponseEntity<>("An account for that email already exists.", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e){
            return new ResponseEntity<>("EmailError", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return new ResponseEntity<>("Successful Registration", HttpStatus.OK);
    }

    @GetMapping("api/patient/registrationConfirm/{token}")
    public ResponseEntity<String> confirmRegistration(@PathVariable String token) {
        VerificationToken verificationToken = patientService.getVerificationToken(token);
        if (verificationToken == null) {
            return new ResponseEntity<>("Invalid activation link", HttpStatus.BAD_REQUEST);
        }

        Patient patient = verificationToken.getPatient();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return new ResponseEntity<>("Invalid activation link", HttpStatus.BAD_REQUEST);
        }

        patient.setEnabled(true);
        patientService.update(patient);
        return new ResponseEntity<>("Successful registration", HttpStatus.OK);
    }
    
    @GetMapping("api/patient/mail")
    public ResponseEntity<String> sendMail(){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("${mail.username}");
        email.setTo("pakibif270@botfed.com");
        email.setSubject("test");
        // check later
        String message = "This is the confirmation link to finish your registration.";
        email.setText(message);
        mailSender.send(email);
        return ResponseEntity.ok("sent");
    }


}
