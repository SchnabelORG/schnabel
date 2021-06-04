package com.schnabel.schnabel.grades.service;

import java.util.Optional;

import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.service.IDrugService;
import com.schnabel.schnabel.grades.dto.GradeRequest;
import com.schnabel.schnabel.grades.model.DrugGrade;
import com.schnabel.schnabel.grades.repository.IDrugGradeRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.service.IPatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

@Service
public class DrugGradeService extends JpaService<DrugGrade, Long, IDrugGradeRepository> implements IDrugGradeService {

    private final IDrugService drugService;
    private final IPatientService patientService;
    private final JwtUtils jwtUtils;

    @Autowired
    public DrugGradeService(IDrugGradeRepository repository, IDrugService drugService, IPatientService patientService, JwtUtils jwtUtils) {
        super(repository);
        this.drugService = drugService;
        this.patientService = patientService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public PagedModel<DrugDTO> findGradeable(String authHeader, Pageable pageable) {
        String email = jwtUtils.getEmailFromAuth(authHeader);
        Optional<Patient> patient = patientService.findByEmail(email);
        if(!patient.isPresent()) {
            return PagedModel.empty();
        }

        return drugService.findGradeable(patient.get().getId(), pageable);
    }

    @Override
    public boolean gradeDrug(GradeRequest req, String authHeader) {
        if(req.getValue() < 0 || req.getValue() > 5) {
            return false;
        }
        String email = jwtUtils.getEmailFromAuth(authHeader);
        Optional<Patient> patient = patientService.findByEmail(email);
        Optional<Drug> drug = drugService.get(req.getTargetId());
        if(!patient.isPresent() || !drug.isPresent()) {
            return false;
        }

        if(!patientService.isAllowedToGradeDrug(patient.get().getId(), req.getTargetId())) {
            return false;
        }

        Optional<DrugGrade> grade = repository.findByDrugIdAndPatientId(req.getTargetId(), patient.get().getId());
        if(grade.isPresent()) {
            grade.get().setValue(req.getValue());
            if(!update(grade.get())) {
                return false;
            }
            drug.get().setScore(repository.findAvg(drug.get().getId()));
            return drugService.update(drug.get());
        } else {
            DrugGrade newGrade = new DrugGrade(req.getValue(), patient.get(), drug.get());
            add(newGrade);
            drug.get().setScore(repository.findAvg(drug.get().getId()));
            return drugService.update(drug.get());
        }
    }
    
}
