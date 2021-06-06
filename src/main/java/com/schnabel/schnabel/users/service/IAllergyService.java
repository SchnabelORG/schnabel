package com.schnabel.schnabel.users.service;

import java.util.List;
import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.dto.AllergyDTO;
import com.schnabel.schnabel.users.model.Allergy;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface IAllergyService extends IJpaService<Allergy, Long> {

    Optional<AllergyDTO> findByIdDTO(Long id);
    PagedModel<AllergyDTO> findAllDTO(Pageable pageable);
    PagedModel<AllergyDTO> findByPatientId(String authHeader, Pageable pageable);
    boolean addToPatient(String authHeader, Long drugId);
    List<String> findPotentialAllergies();
}
