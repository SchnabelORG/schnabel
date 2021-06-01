package com.schnabel.schnabel.drugs.controller;


import com.schnabel.schnabel.drugs.dto.DrugReservationAssembler;
import com.schnabel.schnabel.drugs.dto.DrugReservationDTO;
import com.schnabel.schnabel.drugs.model.DrugReservation;
import com.schnabel.schnabel.drugs.service.IDrugReservationService;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;
import com.schnabel.schnabel.pharmacies.service.IWareHouseItemService;
import com.schnabel.schnabel.security.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/dreservation")
public class DrugReservationController {

    private final IDrugReservationService drugReservationService;
    private final IWareHouseItemService wareHouseItemService;
    private final DrugReservationAssembler drugReservationAssembler;

    @Autowired
    public DrugReservationController(IDrugReservationService drugReservationService, IWareHouseItemService wareHouseItemService, DrugReservationAssembler drugReservationAssembler, PagedResourcesAssembler<DrugReservation> drugReservationPagedResourcesAssembler, JwtUtils jwtUtils) {
        this.drugReservationService = drugReservationService;
        this.wareHouseItemService = wareHouseItemService;
        this.drugReservationAssembler = drugReservationAssembler;
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
        /*Optional<DrugReservation> drugReservation = drugReservationService.get(reservationId);
        if (drugReservation.isPresent()){
            Optional<WareHouseItem> wareHouseItem = wareHouseItemService.findWareHouseItemByPharmacyAndDrugId(drugReservation.get().getDrug().getId(), drugReservation.get().getPharmacy().getId());
            if(wareHouseItem.isPresent()){
                drugReservation.get().setTaken(true);
                drugReservationService.update(drugReservation.get());
                wareHouseItem.get().setQuantity(wareHouseItem.get().getQuantity() - drugReservation.get().getQuantity());
                wareHouseItemService.update(wareHouseItem.get());
                return ResponseEntity.ok("Successfully");
            }
            return ResponseEntity.notFound().build();
        }*/
        return ResponseEntity.notFound().build();
    }
}
