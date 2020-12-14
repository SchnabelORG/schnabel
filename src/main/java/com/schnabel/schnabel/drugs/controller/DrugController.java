package com.schnabel.schnabel.drugs.controller;

import java.util.List;

import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.repository.IDrugRepository;
import com.schnabel.schnabel.drugs.service.IDrugService;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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
}
