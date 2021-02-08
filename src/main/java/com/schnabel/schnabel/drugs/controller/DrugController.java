package com.schnabel.schnabel.drugs.controller;

import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.model.UrgentProcurementPequest;
import com.schnabel.schnabel.drugs.service.IDrugService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Drug REST controller
 */
// TODO(Jovan): Remove pswapi
@RestController
public class DrugController
{
    private final IDrugService drugService;

    @Autowired
    public DrugController(IDrugService drugService)
    {
        this.drugService = drugService;
    }

    /**
     * Get drug with <b>id</b>
     * @param id ID of queried Drug
     * @return 
     * OK with Drug body if exists
     * else BAD_REQUEST
     */
    @GetMapping("/pswapi/drugs/{id}")
    public ResponseEntity<Drug> getById(@PathVariable long id)
    {
        Drug drug = drugService.get(id);
        return drug == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(drug);
    }

    /**
     * Check if drug with <b>name</b> exists
     * @param name Name of queried drug
     * @return
     * OK if exists
     * else BAD_REQUEST
     */
    @GetMapping("/pswapi/drugs/name/{name}")
    public ResponseEntity<String> getById(@PathVariable String name)
    {
        return drugService.getByName(name) ?
                ResponseEntity.ok("We have the desired medication!")
            : ResponseEntity.ok("We don't have that medication!");
    }

    /**
     * Get all drugs
     * @return Iterable of Drug
     */
    @GetMapping("/pswapi/drugs")
    public ResponseEntity<Iterable<Drug>> getAll()
    {
        return ResponseEntity.ok(drugService.getAll());
    }

    /**
     * Create urgent procurement request for a drug
     * @param urgentProcurementRequest Request to be created
     * @return OK
     */
    @PostMapping("/pswapi/drugs/urgent")
    public ResponseEntity<String> getUrgentRequest(@RequestBody UrgentProcurementPequest urgentProcurementRequest)
    {
        return  ResponseEntity.ok("Arrives as soon as possible!!!");
    }

}
