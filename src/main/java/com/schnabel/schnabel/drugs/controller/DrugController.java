package com.schnabel.schnabel.drugs.controller;


import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.drugs.service.IDrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/drug")
public class DrugController {

    private final IDrugService drugService;

    @Autowired
    public DrugController(IDrugService drugService) {
        this.drugService = drugService;
    }

    /**
     * Get drug by id
     * @param id
     * @return Drug
     */
    @GetMapping("{id}")
    public ResponseEntity<DrugDTO> get(@PathVariable long id) {
        return drugService.getDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     *  Get all drugs
     * @return Page of Drug
     */
    @GetMapping()
    public ResponseEntity<PagedModel<DrugDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(drugService.getAllDTO(pageable), HttpStatus.OK);
    }

    /**
     * Register new drug
     * @param registrationDTO
     * @return Ok if drug is registered, else bad request
     */
    @PostMapping
    public ResponseEntity<String> registerDrug(@RequestBody DrugDTO registrationDTO)
    {
        return drugService.registerDrug(registrationDTO.getCode(), registrationDTO.getName(), registrationDTO.getDescription(), registrationDTO.getDrugState(), registrationDTO.getDrugOrigin(), registrationDTO.getDrugType(), registrationDTO.getProducer(), registrationDTO.getDosage(),registrationDTO.getIngredients(), registrationDTO.getIssuingType()) ?
                ResponseEntity.ok("Registered")
                : ResponseEntity.badRequest().build();
    }

}
