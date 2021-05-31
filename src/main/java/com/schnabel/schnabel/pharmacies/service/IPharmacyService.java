package com.schnabel.schnabel.pharmacies.service;

import java.util.Map;
import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.dto.PharmacyCreationDTO;
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
    boolean registerPharmacy(PharmacyCreationDTO creationDTO);
    Optional<Pharmacy> findByName(String name);
}
