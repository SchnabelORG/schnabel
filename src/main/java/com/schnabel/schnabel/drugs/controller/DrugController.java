package com.schnabel.schnabel.drugs.controller;

import java.util.List;

import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.model.UrgentProcurementPequest;
import com.schnabel.schnabel.drugs.repository.IDrugRepository;
import com.schnabel.schnabel.drugs.service.IDrugService;

import com.schnabel.schnabel.pswusagereport.model.UsageReportNotification;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

@RestController
public class DrugController
{
    private final IDrugService _drugService;

    @Autowired
    public DrugController(IDrugService drugService)
    {
        this._drugService = drugService;
    }

    @GetMapping("/pswapi/drugs/{id}")
    public ResponseEntity<Drug> getById(@PathVariable int id)
    {
        Drug drug = _drugService.get(id);
        return drug == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(drug);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/pswapi/drugs/name/{name}")
    public ResponseEntity<String> getById(@PathVariable String name)
    {
        return _drugService.getByName(name) ?
                ResponseEntity.ok("We have the desired medication!")
            : ResponseEntity.ok("We don't have that medication!");
    }

    @GetMapping("/pswapi/drugs")
    public ResponseEntity<List<Drug>> getAll()
    {
        return ResponseEntity.ok(_drugService.getAll());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/pswapi/drugs/urgent")
    public ResponseEntity<String> getUrgentRequest(@RequestBody UrgentProcurementPequest urgentProcurementRequest)
    {
        return  ResponseEntity.ok("Arrives as soon as possible!!!");
    }

}
