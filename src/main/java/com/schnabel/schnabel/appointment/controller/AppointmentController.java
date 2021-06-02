package com.schnabel.schnabel.appointment.controller;
import com.schnabel.schnabel.appointment.dto.AppointmentDTO;
import com.schnabel.schnabel.appointment.dto.AppointmentRequest;
import com.schnabel.schnabel.appointment.service.IAppointmentService;
import com.schnabel.schnabel.security.util.JwtUtils;
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
    private final JwtUtils jwtUtils;

    @Autowired
    public AppointmentController(IAppointmentService service, JwtUtils jwtUtils) {
        this.service = service;
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

}
