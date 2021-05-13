package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.misc.exceptions.PatientAlreadyExistsException;
import com.schnabel.schnabel.registration.dto.UserDTO;
import com.schnabel.schnabel.users.dto.PatientDTO;
import com.schnabel.schnabel.users.dto.PatientDTOAssembler;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.registration.model.VerificationToken;
import com.schnabel.schnabel.users.service.IPatientService;

import com.schnabel.schnabel.registration.util.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Calendar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Patient REST controller
 */
@RestController
@RequestMapping("api/patient")
public class PatientController
{
    private final IPatientService patientService;
    private final PatientDTOAssembler patientDTOAsm;
    private final PagedResourcesAssembler<Patient> patientPageAsm;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    public PatientController(IPatientService patientService, PatientDTOAssembler patientDTOAsm, PagedResourcesAssembler<Patient> patientPageAsm)
    {
        this.patientService = patientService;
        this.patientDTOAsm = patientDTOAsm;
        this.patientPageAsm = patientPageAsm;
    }

    /**
     * Get patient by id
     * @return Patient
     */
    @GetMapping("{id}")
    public ResponseEntity<PatientDTO> get(@PathVariable long id)
    {
        return patientService.get(id).map(patientDTOAsm::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all patients
     * @return Iterable of Patients
     */
    @GetMapping
    public ResponseEntity<PagedModel<PatientDTO>> getAll(Pageable pageable)
    {
        Page<Patient> patients = patientService.getAll(pageable);
        PagedModel<PatientDTO> collModel = patientPageAsm.toModel(patients, patientDTOAsm);
        return new ResponseEntity<>(collModel, HttpStatus.OK);
    }

    @PostMapping("registration")
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

    @GetMapping("registrationConfirm/{token}")
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
    
    @GetMapping("mail")
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
