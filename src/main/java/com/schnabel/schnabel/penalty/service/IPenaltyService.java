package com.schnabel.schnabel.penalty.service;

import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.penalty.dto.PenaltyDTO;
import com.schnabel.schnabel.penalty.model.Penalty;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface IPenaltyService extends IJpaService<Penalty, Long> {
    Optional<PenaltyDTO> getDTO(Long id);
    PagedModel<PenaltyDTO> findByPatientId(String authHeader, Pageable pageable);
    PagedModel<PenaltyDTO> findCurrentPenalties(String authHeader, Pageable pageable);
    /**
     * Checks for missed appointments and other penalty causes
     * and adds non-existant penalties.
     * Usually called per login. Maybe per refresh?
     */
    boolean generatePenalties(String email);
}
