package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.users.dto.PatientDTO;
import com.schnabel.schnabel.users.dto.PatientDTOAssembler;
import com.schnabel.schnabel.users.dto.RegisterRequest;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.service.IPatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public ResponseEntity<String> registerPatient(@RequestBody RegisterRequest req) {
        return patientService.registerPatient(req.getName(), req.getSurname(), req.getEmail(), req.getPassword(), req.getAddress(), req.getPhoneNo()) ?
            ResponseEntity.ok("Registered")
            : ResponseEntity.badRequest().build();

    }
}
