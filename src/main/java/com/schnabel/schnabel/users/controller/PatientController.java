package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.appointment.dto.AppointmentDTO;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.*;
import com.schnabel.schnabel.users.service.IPatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Patient REST controller
 */
@RestController
@RequestMapping("api/patient")
public class PatientController
{
    private final IPatientService service;
    private final PatientDTOAssembler patientDTOAsm;
    private final JwtUtils jwtUtils;

    @Autowired
    public PatientController(IPatientService service, PatientDTOAssembler patientDTOAsm, JwtUtils jwtUtils)
    {
        this.service = service;
        this.patientDTOAsm = patientDTOAsm;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Get patient by id
     * @return Patient
     */
    @GetMapping("{id}")
    public ResponseEntity<PatientDTO> get(@PathVariable long id)
    {
        return service.get(id)
            .map(patientDTOAsm::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // /**
    //  * Get all patients
    //  * @return Iterable of Patients
    //  */
    // @GetMapping
    // public ResponseEntity<PagedModel<PatientDTO>> getAll(Pageable pageable)
    // {
    //     Page<Patient> patients = patientService.getAll(pageable);
    //     PagedModel<PatientDTO> collModel = patientPageAsm.toModel(patients, patientDTOAsm);
    //     return new ResponseEntity<>(collModel, HttpStatus.OK);
    // }
    /**
     * Get patient by JWT
     * @param authHeader - Authorization header containing jwt
     * @return PatientDTO if exists, else BadRequest
     */
    @GetMapping
    public ResponseEntity<PatientDTO> getByJws(@RequestHeader("Authorization") String authHeader) {
        String jws;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jws = authHeader.substring(7, authHeader.length());
        } else {
            return ResponseEntity.badRequest().build();
        }

        String email = jwtUtils.getEmailFromJws(jws);
        return service.findByEmail(email)
            .map(patientDTOAsm::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Register new patient
     * @param req - Patient registration request containing required info
     * @return OK if registered, else BadRequest
     */
    @PostMapping
    public ResponseEntity<String> registerPatient(@RequestBody RegisterRequest req) {
        return service.registerPatient(req.getName(), req.getSurname(), req.getEmail(), req.getPassword(), req.getAddress(), req.getPhoneNo()) ?
            ResponseEntity.ok("Registered")
            : ResponseEntity.badRequest().build();

    }

    @PostMapping("update")
    public ResponseEntity<String> updateUser(@RequestBody PatientDTO req) {
        return service.update(req) ?
            ResponseEntity.ok("Updated")
            : ResponseEntity.badRequest().build();
    }

    @GetMapping("apptderm")
    public ResponseEntity<PagedModel<AppointmentDTO>> getAppointments(Pageable pageable, @RequestHeader("Authorization") String auth) {
        String email = jwtUtils.getEmailFromJws(jwtUtils.parseJwtFromAuthorizationHeader(auth));
        return new ResponseEntity<>(service.findDermAppts(email, pageable), HttpStatus.OK);
    }

    @PostMapping("apptderm")
    public ResponseEntity<String> scheduleDermAppt(@RequestBody long apptId, @RequestHeader("Authorization") String auth) {
        String jws = jwtUtils.parseJwtFromAuthorizationHeader(auth);
        return service.scheduleDermAppt(apptId, jwtUtils.getEmailFromJws(jws)) ?
            ResponseEntity.ok("Scheduled")
            : ResponseEntity.badRequest().build();
    }

    @GetMapping("consult")
    public ResponseEntity<PagedModel<AppointmentDTO>> getConsults(Pageable pageable, @RequestHeader("Authorization") String auth) {
        String email = jwtUtils.getEmailFromAuth(auth);
        return ResponseEntity.ok(service.findConsults(email, pageable));
    }

    /**
     * Schedules pharmacist consult
     */
    @PostMapping("consult")
    public ResponseEntity<String> scheduleConsult(@RequestBody ConsultRequest req, @RequestHeader("Authorization") String auth) {
        String email = jwtUtils.getEmailFromJws(jwtUtils.parseJwtFromAuthorizationHeader(auth));
        return service.scheduleConsult(req, email) ?
            ResponseEntity.ok("Scheduled")
            : ResponseEntity.badRequest().build();
    }

    @PostMapping("appointemnt/cancel")
    public ResponseEntity<String> cancelAppointment(@RequestBody long apptId, @RequestHeader("Authorization") String auth) {
        String email = jwtUtils.getEmailFromJws(jwtUtils.parseJwtFromAuthorizationHeader(auth));
        return service.cancelAppointment(apptId, email) ?
            ResponseEntity.ok("Cancelled")
            : ResponseEntity.badRequest().build();
    }

    /**
     * Creates a drug reservation
     */
    @PostMapping("resdrug")
    public ResponseEntity<String> reserveDrug(@RequestBody DrugReservationRequest req, @RequestHeader("Authorization") String auth) {
        String email = jwtUtils.getEmailFromJws(jwtUtils.parseJwtFromAuthorizationHeader(auth));
        return service.reserveDrug(req, email) ?
            ResponseEntity.ok("Reserved")
            : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("subscribed/{id}")
    public ResponseEntity<?> isSubscribed(@PathVariable long id, @RequestHeader("Authorization") String auth) {
        String email = jwtUtils.getEmailFromJws(jwtUtils.parseJwtFromAuthorizationHeader(auth));
        boolean b = service.isSubscribed(email, id);
        return b ?
                ResponseEntity.ok("Subscribed")
                : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("subscribe/{id}")
    public ResponseEntity<?> subscribe(@PathVariable long id, @RequestHeader("Authorization") String auth) {
        String email = jwtUtils.getEmailFromJws(jwtUtils.parseJwtFromAuthorizationHeader(auth));
        boolean b = service.subscribe(email, id);
        return b ?
                ResponseEntity.ok("Subscribed")
                : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("subscriptions")
    public ResponseEntity<Collection<PharmacyDTO>> subscriptions(@RequestHeader("Authorization") String auth) {
        String email = jwtUtils.getEmailFromJws(jwtUtils.parseJwtFromAuthorizationHeader(auth));
        return ResponseEntity.ok(service.getSubscritions(email));
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("unsubscribe/{id}")
    public ResponseEntity<?> unsubscribe(@PathVariable long id, @RequestHeader("Authorization") String auth) {
        String email = jwtUtils.getEmailFromJws(jwtUtils.parseJwtFromAuthorizationHeader(auth));
        boolean b = service.unsubscribe(email, id);
        return b ?
                ResponseEntity.ok("Subscribed")
                : ResponseEntity.badRequest().build();
    }




}
