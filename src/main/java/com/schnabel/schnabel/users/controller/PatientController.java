package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.PatientDTO;
import com.schnabel.schnabel.users.dto.PatientDTOAssembler;
import com.schnabel.schnabel.users.dto.RegisterRequest;
import com.schnabel.schnabel.users.service.IPatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    private final JwtUtils jwtUtils;

    @Autowired
    public PatientController(IPatientService patientService, PatientDTOAssembler patientDTOAsm, JwtUtils jwtUtils)
    {
        this.patientService = patientService;
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
        return patientService.get(id).map(patientDTOAsm::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
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
        return patientService.findByEmail(email)
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
        return patientService.registerPatient(req.getName(), req.getSurname(), req.getEmail(), req.getPassword(), req.getAddress(), req.getPhoneNo()) ?
            ResponseEntity.ok("Registered")
            : ResponseEntity.badRequest().build();

    }

    @PostMapping("appointment")
    public ResponseEntity<String> scheduleAppointment(@RequestBody long apptId, @RequestHeader("Authorization") String auth) {
        String jws = jwtUtils.parseJwtFromAuthorizationHeader(auth);
        return patientService.scheduleAppointment(apptId, jwtUtils.getEmailFromJws(jws)) ?
            ResponseEntity.ok("Scheduled")
            : ResponseEntity.badRequest().build();
    }
}
