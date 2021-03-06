package com.schnabel.schnabel.pharmacies.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

/**
 * Pharmacy service interface
 */
public interface IPharmacyService extends IJpaService<Pharmacy, Long>
{
    Optional<PharmacyDTO> getDTO(Long id);
    PagedModel<PharmacyDTO> getAllDTO(Pageable pageable);
    PagedModel<PharmacyDTO> filteredSearch(Map<String, String> params, Pageable pageable);
    Optional<Pharmacy> findByName(String name);
    PagedModel<PharmacyDTO> findByFreePharmacistAppointment(LocalDateTime startTime, Pageable pageable);
    PagedModel<PharmacyDTO> findWithStock(Long drugId, Pageable pageable);
    PagedModel<PharmacyDTO> findGraded(Long patientId, Pageable pageable);
    PagedModel<PharmacyDTO> findGradeable(Long patientId, Pageable pageable);
}

