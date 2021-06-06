package com.schnabel.schnabel.users.controller;

import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.*;
import com.schnabel.schnabel.users.model.MedicalEmployee;
import com.schnabel.schnabel.users.model.Vacation;
import com.schnabel.schnabel.users.model.VacationStatus;
import com.schnabel.schnabel.users.repository.IMedicalEmployee;
import com.schnabel.schnabel.users.service.IMedicalEmployeeService;
import com.schnabel.schnabel.users.service.IVacationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Vacation REST controller
 */
@RestController
@RequestMapping("api/vacation")
public class VacationController 
{
    private final IVacationService vacationService;
    private final IPharmacyService pharmacyService;
    private final IMedicalEmployeeService medicalEmployeeservice;
    private final JwtUtils jwtUtils;

    @Autowired
    public VacationController(IVacationService vacationService, IPharmacyService pharmacyService, IMedicalEmployeeService medicalEmployeeservice, JwtUtils jwtUtils)
    {
        this.vacationService = vacationService;
        this.pharmacyService = pharmacyService;
        this.medicalEmployeeservice = medicalEmployeeservice;
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

    @PostMapping("makenew")
    public ResponseEntity<Boolean> getFreeByPharmacyId(@RequestBody NewVacationDTO dto) {
        Vacation vacation = new Vacation();

        Optional<Pharmacy> pharmacy = pharmacyService.get(dto.getPharmacyId());
        Optional<MedicalEmployee> employee = medicalEmployeeservice.get(dto.getEmployeeId());
        if(pharmacy.isPresent() && employee.isPresent()){
            vacation.setPharmacy(pharmacy.get());
            vacation.setMedicalEmployee(employee.get());
            vacation.setPeriod(new Period(dto.getStartTime(), dto.getEndTime()));
            vacation.setVacationStatus(VacationStatus.CREATED);
            vacationService.add(vacation);
            return ResponseEntity.ok(Boolean.TRUE);
        }
        return ResponseEntity.ok(Boolean.FALSE);
    }

}
