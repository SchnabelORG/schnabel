package com.schnabel.schnabel.drugs.controller;


import com.schnabel.schnabel.drugs.dto.DrugReservationAssembler;
import com.schnabel.schnabel.drugs.dto.DrugReservationDTO;
import com.schnabel.schnabel.drugs.model.DrugReservation;
import com.schnabel.schnabel.drugs.service.IDrugReservationService;
import com.schnabel.schnabel.email.service.IMailService;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;
import com.schnabel.schnabel.pharmacies.service.IWareHouseItemService;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.service.IPatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/dreservation")
public class DrugReservationController {

    private final IDrugReservationService drugReservationService;
    private final IWareHouseItemService wareHouseItemService;
    private final DrugReservationAssembler drugReservationAssembler;
    private final IPatientService patientService;
    private final JwtUtils jwtUtils;
    private final IMailService mailService;

    @Autowired
    public DrugReservationController(IDrugReservationService drugReservationService, IWareHouseItemService wareHouseItemService, DrugReservationAssembler drugReservationAssembler, PagedResourcesAssembler<DrugReservation> drugReservationPagedResourcesAssembler, JwtUtils jwtUtils, IPatientService patientService, IMailService mailService) {
        this.drugReservationService = drugReservationService;
        this.wareHouseItemService = wareHouseItemService;
        this.drugReservationAssembler = drugReservationAssembler;
        this.patientService = patientService;
        this.jwtUtils = jwtUtils;
        this.mailService = mailService;
    }


    /**
     * Get drug reservation by id
     * @return DrugReservationDTO
     */
    @GetMapping("{resrvationId}/{pharmacyId}")
    public ResponseEntity<DrugReservationDTO> get(@PathVariable long resrvationId, @PathVariable long pharmacyId)
    {
        Optional<DrugReservationDTO> drugReservation = drugReservationService.get(resrvationId).map(drugReservationAssembler::toModel);
        if (drugReservation.isPresent()
            && drugReservation.get().getPharmacyId() == pharmacyId
            && !drugReservation.get().isTaken()){
            return drugReservation.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("dispensing/{reservationId}")
    public ResponseEntity<String> dispensing(@PathVariable long reservationId)
    {
        Optional<DrugReservation> drugReservation = drugReservationService.get(reservationId);
        if (drugReservation.isPresent()){
            Optional<WareHouseItem> wareHouseItem = wareHouseItemService.findWareHouseItemByPharmacyAndDrugId(drugReservation.get().getDrug().getId(), drugReservation.get().getPharmacy().getId());
            if(wareHouseItem.isPresent()){
                drugReservation.get().setTaken(true);
                drugReservationService.update(drugReservation.get());
                wareHouseItem.get().setQuantity(wareHouseItem.get().getQuantity() - drugReservation.get().getQuantity());
                wareHouseItemService.update(wareHouseItem.get());
                mailService.sendReservationConfirmation(drugReservation.get().getPatient().getEmail(), "Reservation number "+reservationId+"successfully taken");
                return ResponseEntity.ok("Successfully");
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("patient")
    public ResponseEntity<PagedModel<DrugReservationDTO>> getPatientReservations(@RequestHeader("Authorization") String auth, Pageable pageable) {
        String email = jwtUtils.getEmailFromAuth(auth);
        Optional<Patient> patient = patientService.findByEmail(email);
        if(!patient.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(drugReservationService.findByPatientId(patient.get().getId(), pageable));
    } 

    /**
     * Cancel drug reservation
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable("id") Long resId, @RequestHeader("Authorization") String auth) {
        String email = jwtUtils.getEmailFromAuth(auth);
        Optional<Patient> patient = patientService.findByEmail(email);
        if(!patient.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return drugReservationService.cancelReservation(patient.get().getId(), resId) ?
            ResponseEntity.ok("Cancelled")
            : ResponseEntity.badRequest().build();
    }
}
