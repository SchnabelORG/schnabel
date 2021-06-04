package com.schnabel.schnabel.grades.service;

import java.util.Optional;

import com.schnabel.schnabel.grades.dto.GradeRequest;
import com.schnabel.schnabel.grades.model.EmployeeGrade;
import com.schnabel.schnabel.grades.repository.IEmployeeGradeRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.MedicalEmployeeDTO;
import com.schnabel.schnabel.users.model.MedicalEmployee;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.service.IMedicalEmployeeService;
import com.schnabel.schnabel.users.service.IPatientService;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

@Service
public class EmployeeGradeService extends JpaService<EmployeeGrade, Long, IEmployeeGradeRepository> implements IEmployeeGradeService {

    private final IPatientService patientService;
    private final IMedicalEmployeeService employeeService;
    private final JwtUtils jwtUtils;

    public EmployeeGradeService(IEmployeeGradeRepository repository, IMedicalEmployeeService employeeService, JwtUtils jwtUtils, IPatientService patientService) {
        super(repository);
        this.employeeService = employeeService;
        this.jwtUtils = jwtUtils;
        this.patientService = patientService;
    }


    @Override
    public PagedModel<MedicalEmployeeDTO> findGradeable(String authHeader, Pageable pageable) {

        String email = jwtUtils.getEmailFromAuth(authHeader);
        Optional<Patient> patient = patientService.findByEmail(email);
        if(!patient.isPresent()) {
            return PagedModel.empty();
        }
        return employeeService.findGradeable(patient.get().getId(), pageable);
    }


    @Override
    public boolean gradeEmployee(GradeRequest req, String authHeader) {
        if(req.getValue() < 0 || req.getValue() > 5) {
            return false;
        }
        String email = jwtUtils.getEmailFromAuth(authHeader);
        Optional<Patient> patient = patientService.findByEmail(email);
        Optional<MedicalEmployee> employee = employeeService.get(req.getTargetId());
        if(!patient.isPresent() || !employee.isPresent()) {
            return false;
        }
        if(!patientService.isAllowedToGradeEmployee(patient.get().getId(), req.getTargetId())) {
            return false;
        }
        Optional<EmployeeGrade> grade = repository.findByMedicalEmployeeIdAndPatientId(req.getTargetId(), patient.get().getId());
        if(grade.isPresent()) {
            grade.get().setValue(req.getValue());
            if(!update(grade.get())){
                return false;
            }
            employee.get().setScore(repository.findAvg(employee.get().getId()));
            return employeeService.update(employee.get());
        } else {
            EmployeeGrade newGrade = new EmployeeGrade(req.getValue(), employee.get(), patient.get());
            add(newGrade);
            employee.get().setScore(repository.findAvg(employee.get().getId()));
            return employeeService.update(employee.get());
        }
    }
    
}
