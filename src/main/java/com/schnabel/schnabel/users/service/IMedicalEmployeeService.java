package com.schnabel.schnabel.users.service;

import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.dto.MedicalEmployeeDTO;
import com.schnabel.schnabel.users.model.MedicalEmployee;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface IMedicalEmployeeService extends IJpaService<MedicalEmployee, Long> {
    Optional<MedicalEmployeeDTO> getDTO(Long id);
    Optional<MedicalEmployee> findByEmail(String email);
    PagedModel<MedicalEmployeeDTO> findGradeable(Long patientId, Pageable pageable);
}
