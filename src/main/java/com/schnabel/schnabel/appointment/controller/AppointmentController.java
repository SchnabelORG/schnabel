package com.schnabel.schnabel.appointment.controller;
import java.util.List;
import java.util.Optional;

import com.schnabel.schnabel.appointment.dto.AppointmentDTO;
import com.schnabel.schnabel.appointment.dto.AppointmentRequest;
import com.schnabel.schnabel.appointment.dto.NewAppointmentDTO;
import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.appointment.service.IAppointmentService;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Appointment REST controller
 */
@RestController
@RequestMapping("api/appointment")
public class AppointmentController {

    private final IAppointmentService service;
    private final IPatientService patientService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AppointmentController(IAppointmentService service, IPatientService patientService, JwtUtils jwtUtils) {
        this.service = service;
        this.patientService = patientService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("appbyemployee/{id}")
    public ResponseEntity<PagedModel<AppointmentDTO>> getAllByEmployee(@PathVariable long id, Pageable pageable)
    {
        return new ResponseEntity<>(service.getAllbyPharmacist(pageable, id), HttpStatus.OK);
    }

    /**
     * Get appointment by id
     * @return AppointmentDOT
     */
    @GetMapping("{id}")
    public ResponseEntity<AppointmentDTO> get(@PathVariable("id") Long id) {
        return service.findByIdDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/dermatology")
    public ResponseEntity<PagedModel<AppointmentDTO>> getDermatologicalAppts(Pageable pageable) {
        return new ResponseEntity<>(service.findFreeDermatologistAppointments(pageable), HttpStatus.OK);
    }

    @GetMapping("/takeapp/{appId}/{patientId}")
    public ResponseEntity<Boolean> setAppointmenTaken(@PathVariable("appId") Long appId, @PathVariable("patientId") Long patientId) {
        Optional<Appointment> appointment = service.get(appId);
        Optional<Patient> patient = patientService.get(patientId);
        if(appointment.isPresent() && patient.isPresent()){
            appointment.get().setPatient(patient.get());
            appointment.get().setFree(false);
            Boolean isSuccess =  service.update(appointment.get());
            return ResponseEntity.ok(isSuccess);
        }
        return ResponseEntity.ok(Boolean.FALSE);
    }

    /**
     * Create new appointment
     * @param req - Appointment creation request containing required info
     * @return OK if created, else BadRequest
     */
    @PostMapping
    public ResponseEntity<String> defineAppointment(@RequestBody AppointmentRequest req, @RequestHeader("Authorization") String authHeader)
    {
        String jws = jwtUtils.parseJwtFromAuthorizationHeader(authHeader);
        return service.defineAppointment(req.getStartTime(), req.getEndTime(), req.getPrice(), req.getDermatologistId(), jwtUtils.getEmailFromJws(jws)) ?
            ResponseEntity.ok("Added")
            : ResponseEntity.badRequest().build();
    }

    /**
     * Get patient's dermatology appt. history
     */
    @GetMapping("patient/dermatology")
    public ResponseEntity<PagedModel<AppointmentDTO>> getDermatologyHistory(@RequestHeader("Authorization") String auth, Pageable pageable) {
        String email = jwtUtils.getEmailFromAuth(auth);
        Optional<Patient> patient = patientService.findByEmail(email);
        if(!patient.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.findDermHistory(patient.get().getId(), pageable));
    }

    /**
     * Get patient's pharmacy appt. history
     */
    @GetMapping("patient/consult")
    public ResponseEntity<PagedModel<AppointmentDTO>> getConsultHistory(@RequestHeader("Authorization") String auth, Pageable pageable) {
        String email = jwtUtils.getEmailFromAuth(auth);
        Optional<Patient> patient = patientService.findByEmail(email);
        if(!patient.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.findConsultHistory(patient.get().getId(), pageable));
    }

    @PostMapping("pharmacist/newapp")
    public ResponseEntity<Boolean> makeNewAppointment(@RequestBody NewAppointmentDTO newAppointment)
    {
        Optional<Patient> patient = patientService.get(newAppointment.getPatientId());
        Boolean isSuccess = service.makeNewAppAsPharmacist(newAppointment, patient.get());

        return  ResponseEntity.ok(isSuccess);
    }

    @GetMapping("missed/{appId}")
    public ResponseEntity<Boolean> setMissedAppointment( @PathVariable("appId") Long id) {
        Optional<Appointment> appointment = service.get(id);
        if (appointment.isPresent()) {
            appointment.get().setMissed(true);
            service.update(appointment.get());
            return ResponseEntity.ok(Boolean.TRUE);
        }
        return ResponseEntity.ok(Boolean.FALSE);
    }
    @GetMapping("/dermatology/pharmacy/{id}")
    public ResponseEntity<PagedModel<AppointmentDTO>> getDermatologicalApptsByPharmacy(@PathVariable("id") Long id, Pageable pageable) {
        return new ResponseEntity<>(service.findFreeDermatologistAppointmentsByPharmacy(id, pageable), HttpStatus.OK);
    }

    @GetMapping("/pharmacy/{id}")
    public ResponseEntity<PagedModel<AppointmentDTO>> getAllByPharmacy(@PathVariable("id") Long id, Pageable pageable) {
        return new ResponseEntity<>(service.findByPharmacyId(id, pageable), HttpStatus.OK);
    }

    @GetMapping("/pharmacymonth/{id}")
    public ResponseEntity<List<Integer>> getCountMonth(@PathVariable("id") Long id, Pageable pageable) {
        return new ResponseEntity<>(service.countAppointmentsByMonth(id, pageable), HttpStatus.OK);
    }

    @GetMapping("/pharmacyyear/{id}")
    public ResponseEntity<List<Integer>> getCountYear(@PathVariable("id") Long id, Pageable pageable) {
        return new ResponseEntity<>(service.countAppointmentsByYear(id, pageable), HttpStatus.OK);
    }

    @GetMapping("/pharmacyincomemonth/{id}")
    public ResponseEntity<List<Double>> getIncomeMonth(@PathVariable("id") Long id, Pageable pageable) {
        return new ResponseEntity<>(service.countIncomeByMonth(id, pageable), HttpStatus.OK);
    }

    @GetMapping("/pharmacyincomeyear/{id}")
    public ResponseEntity<List<Double>> getIncomeYear(@PathVariable("id") Long id, Pageable pageable) {
        return new ResponseEntity<>(service.countIncomeByYear(id, pageable), HttpStatus.OK);
    }

}
