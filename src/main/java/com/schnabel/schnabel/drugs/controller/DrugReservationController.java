package com.schnabel.schnabel.drugs.controller;


import com.schnabel.schnabel.drugs.dto.DrugReservationAssembler;
import com.schnabel.schnabel.drugs.dto.DrugReservationDTO;
import com.schnabel.schnabel.drugs.model.DrugReservation;
import com.schnabel.schnabel.drugs.service.IDrugReservationService;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.PatientDTO;
import com.schnabel.schnabel.users.dto.PatientDTOAssembler;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dreservation")
public class DrugReservationController {

    private final IDrugReservationService drugReservationService;
    private final DrugReservationAssembler drugReservationAssembler;
    private final PagedResourcesAssembler<DrugReservation> drugReservationPagedResourcesAssembler;
    private final JwtUtils jwtUtils;



    @Autowired
    public DrugReservationController(IDrugReservationService drugReservationService, DrugReservationAssembler drugReservationAssembler, PagedResourcesAssembler<DrugReservation> drugReservationPagedResourcesAssembler, JwtUtils jwtUtils) {
        this.drugReservationService = drugReservationService;
        this.drugReservationAssembler = drugReservationAssembler;
        this.drugReservationPagedResourcesAssembler = drugReservationPagedResourcesAssembler;
        this.jwtUtils = jwtUtils;
    }


    /**
     * Get drug reservation by id
     * @return DrugReservationDTO
     */
    @GetMapping("{id}")
    public ResponseEntity<DrugReservationDTO> get(@PathVariable long id)
    {
        return drugReservationService.get(id).map(drugReservationAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
