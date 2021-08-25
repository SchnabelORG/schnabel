package com.schnabel.schnabel.grades.controller;

import javax.validation.Valid;

import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.grades.dto.GradeRequest;
import com.schnabel.schnabel.grades.service.IDrugGradeService;
import com.schnabel.schnabel.grades.service.IEmployeeGradeService;
import com.schnabel.schnabel.grades.service.IPharmacyGradeService;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import com.schnabel.schnabel.users.dto.MedicalEmployeeDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PharmacyGrade REST controller
 */
@RestController
@RequestMapping("api/grade")
public class GradeController 
{
    private final IPharmacyGradeService pharmacyService;
    private final IEmployeeGradeService employeeService;
    private final IDrugGradeService drugService;

    @Autowired
    public GradeController(IPharmacyGradeService pharmacyService, IEmployeeGradeService employeeService, IDrugGradeService drugService)
    {
        this.pharmacyService = pharmacyService;
        this.employeeService = employeeService;
        this.drugService = drugService;
    }

    @PostMapping("pharmacy")
    public ResponseEntity<String> ratePharmacy(@Valid @RequestBody GradeRequest req, @RequestHeader("Authorization") String auth) {
        return pharmacyService.ratePharmacy(req, auth) ?
            ResponseEntity.ok("Rated")
            : ResponseEntity.badRequest().build();
    }

    @PostMapping("employee")
    public ResponseEntity<String> rateEmployee(@Valid @RequestBody GradeRequest req, @RequestHeader("Authorization") String auth) {
        return employeeService.gradeEmployee(req, auth) ?
            ResponseEntity.ok("Rated")
            : ResponseEntity.badRequest().build();
    }

    @PostMapping("drug")
    public ResponseEntity<String> rateDrug(@Valid @RequestBody GradeRequest req, @RequestHeader("Authorization") String auth) {
        return drugService.gradeDrug(req, auth) ?
            ResponseEntity.ok("Rated")
            : ResponseEntity.badRequest().build();
    }

    @GetMapping("patient/graded_pharmacies")
    public ResponseEntity<PagedModel<PharmacyDTO>> getGradedPharmacies(@RequestHeader ("Authorization") String auth, Pageable pageable) {
        return ResponseEntity.ok(pharmacyService.findGraded(auth, pageable));
    }

    @GetMapping("patient/gradeable_pharmacies")
    public ResponseEntity<PagedModel<PharmacyDTO>> getGradeablePharmacies(@RequestHeader("Authorization") String auth, Pageable pageable) {
        return ResponseEntity.ok(pharmacyService.findGradeable(auth, pageable));
    }

    @GetMapping("patient/gradeable_employees")
    public ResponseEntity<PagedModel<MedicalEmployeeDTO>> getGradeableEmployees(@RequestHeader("Authorization") String auth, Pageable pageable) {
        return ResponseEntity.ok(employeeService.findGradeable(auth, pageable));
    }

    @GetMapping("patient/gradeable_drugs")
    public ResponseEntity<PagedModel<DrugDTO>> getGradeableDrugs(@RequestHeader("Authorization") String auth, Pageable pageable) {
        return ResponseEntity.ok(drugService.findGradeable(auth, pageable));
    }
    
    @GetMapping("pharmacy/avarage/{id}")
    public ResponseEntity<Double> getPharmacyAvarageGrade(@PathVariable("id") Long pharmacyId) {
        return ResponseEntity.ok(pharmacyService.findAvg(pharmacyId));
    }
}