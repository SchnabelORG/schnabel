package com.schnabel.schnabel.drugs.controller;

import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.drugs.dto.DrugRegistrationDTO;
import com.schnabel.schnabel.drugs.dto.DrugUpdateDTO;
import com.schnabel.schnabel.drugs.model.enums.DrugOrigin;
import com.schnabel.schnabel.drugs.model.enums.DrugState;
import com.schnabel.schnabel.drugs.model.enums.DrugType;
import com.schnabel.schnabel.drugs.service.IDrugService;
import java.util.List;
import java.util.Map;

import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.drugs.service.IDrugService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("api/drug")
public class DrugController 
{
    private final IDrugService service;

    @Autowired
    public DrugController(IDrugService drugService) {
        this.service = drugService;
    }

    @Transactional
    @GetMapping("{id}")
    public ResponseEntity<DrugDTO> get(@PathVariable("id") Long id) {
        return service.findByIdDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<PagedModel<DrugDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(service.findAllDTO(pageable), HttpStatus.OK);
    }
    @GetMapping("search")
    public ResponseEntity<PagedModel<DrugDTO>> search(@RequestParam Map<String, String> params, Pageable pageable) {
        return new ResponseEntity<>(service.filteredSearch(params, pageable), HttpStatus.OK);
    }

    @GetMapping("allergy")
    public ResponseEntity<List<DrugDTO>> getAll() {
        return ResponseEntity.ok(service.findAllDTO());
    }

    @PostMapping
    public ResponseEntity<String> registerDrug(@RequestBody DrugRegistrationDTO registrationDTO)
    {
        return service.registerDrug(registrationDTO.getCode(), registrationDTO.getName(), registrationDTO.getDescription(), registrationDTO.getDrugState(), registrationDTO.getDrugOrigin(), registrationDTO.getDrugType(), registrationDTO.getProducer(), registrationDTO.getDosage(), registrationDTO.getIssuingType(), registrationDTO.getSubstituteDrugs()) ?
                ResponseEntity.ok("Registered")
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("types")
    public ResponseEntity<Enum<DrugType>[]> getDrugTypes() {
        return ResponseEntity.ok(service.getDrugTypes());
    }

    @GetMapping("origins")
    public ResponseEntity<Enum<DrugOrigin>[]> getDrugOrigin() {
        return ResponseEntity.ok(service.getDrugOrigin());
    }

    @GetMapping("states")
    public ResponseEntity<Enum<DrugState>[]> getDrugState() {
        return ResponseEntity.ok(service.getDrugState());
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        return service.remove(id) ?
                ResponseEntity.ok("removed")
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("substitute/{id}")
    public ResponseEntity<List<DrugDTO>> getSubstitute(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getSubstitute(id));
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody DrugUpdateDTO dto) {
        return service.updateDrug(dto.getId(), dto.getCode(), dto.getName(), dto.getDescription(), dto.getDrugState(), dto.getDrugOrigin(), dto.getDrugType(), dto.getProducer(), dto.getDosage(), dto.getIssuingType(), dto.getSubstituteDrugs()) ?
                ResponseEntity.ok("updated")
                : ResponseEntity.badRequest().build();
    }
}
