package com.schnabel.schnabel.users.service;

import java.util.Optional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.users.dto.MedicalEmployeeDTO;
import com.schnabel.schnabel.users.dto.MedicalEmployeeDTOAssembler;
import com.schnabel.schnabel.users.model.MedicalEmployee;
import com.schnabel.schnabel.users.repository.IMedicalEmployeeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

@Service
public class MedicalEmployeeService extends JpaService<MedicalEmployee, Long, IMedicalEmployeeRepository> implements IMedicalEmployeeService {

    private final MedicalEmployeeDTOAssembler dtoAsm;
    private final PagedResourcesAssembler<MedicalEmployee> pageAsm;

    public MedicalEmployeeService(IMedicalEmployeeRepository repository, MedicalEmployeeDTOAssembler dtoAsm, PagedResourcesAssembler<MedicalEmployee> pageAsm) {
        super(repository);
        this.dtoAsm = dtoAsm;
        this.pageAsm = pageAsm;
    }

    @Override
    public Optional<MedicalEmployeeDTO> getDTO(Long id) {
        return get(id).map(dtoAsm::toModel);
    }

    @Override
    public Optional<MedicalEmployee> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public PagedModel<MedicalEmployeeDTO> findGradeable(Long patientId, Pageable pageable) {
        Page<MedicalEmployee> employees = repository.findGradeable(patientId, pageable);
        return pageAsm.toModel(employees, dtoAsm);
    }
}
