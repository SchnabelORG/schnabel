package com.schnabel.schnabel.drugs.controller;


import com.schnabel.schnabel.drugs.dto.DrugReservationAssembler;
import com.schnabel.schnabel.drugs.dto.DrugReservationDTO;
import com.schnabel.schnabel.drugs.service.IDrugReservationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public DrugReservationController(IDrugReservationService drugReservationService, DrugReservationAssembler drugReservationAssembler) {
        this.drugReservationService = drugReservationService;
        this.drugReservationAssembler = drugReservationAssembler;
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
