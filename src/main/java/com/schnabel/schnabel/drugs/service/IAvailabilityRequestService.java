package com.schnabel.schnabel.drugs.service;

import java.util.Optional;

import com.schnabel.schnabel.drugs.dto.AvailabilityRequestDTO;
import com.schnabel.schnabel.drugs.model.AvailabilityRequest;
import com.schnabel.schnabel.misc.interfaces.IJpaService;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

/**
 * AvailabilityRequest JPA service interface
 */
public interface IAvailabilityRequestService extends IJpaService<AvailabilityRequest, Long>  
{
    Optional<AvailabilityRequestDTO> findByIdDTO(Long id);
    PagedModel<AvailabilityRequestDTO> findByPharmacyId(Long id, Pageable pageable);
}
