package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.VacationDTO;
import com.schnabel.schnabel.users.dto.VacationRequest;
import com.schnabel.schnabel.users.service.IVacationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Vacation REST controller
 */
@RestController
@RequestMapping("api/vacation")
public class VacationController 
{
    private final IVacationService vacationService;
    private final JwtUtils jwtUtils;

    @Autowired
    public VacationController(IVacationService vacationService, JwtUtils jwtUtils)
    {
        this.vacationService = vacationService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Get vacation by id
     * @return Vacation
     */
    @GetMapping("{id}")
    public ResponseEntity<VacationDTO> get(@PathVariable Long id)
    {
        return vacationService.getDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get created vacations
     * @return PagedModel<Vacation>
     */
    @GetMapping("created")
    public ResponseEntity<PagedModel<VacationDTO>> getCreatedVacations(@RequestHeader("Authorization") String authHeader, Pageable pageable)
    {
        String jws = jwtUtils.parseJwtFromAuthorizationHeader(authHeader);
        return new ResponseEntity<>(vacationService.getCreatedVacationsByPharmacy(jwtUtils.getEmailFromJws(jws), pageable), HttpStatus.OK);
    }

    @PutMapping("accept")
    public ResponseEntity<String> acceptVacation(@RequestBody Long id, @RequestHeader("Authorization") String authHeader)
    {
        return vacationService.acceptVacation(id) ? 
            ResponseEntity.ok("Accepted")
            : ResponseEntity.badRequest().build();
    }

    @PutMapping("reject")
    public ResponseEntity<String> rejectVacation(@RequestBody VacationRequest vacationRequest, @RequestHeader("Authorization") String authHeader)
    {
        return vacationService.rejectVacation(vacationRequest) ? 
            ResponseEntity.ok("Rejected")
            : ResponseEntity.badRequest().build();
    }

}
