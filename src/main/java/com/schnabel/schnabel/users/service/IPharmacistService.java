package com.schnabel.schnabel.users.service;

import java.util.Map;
import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.dto.FreePharmacistLookupRequest;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.model.Pharmacist;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

/**
 * Pharmacists service interface
 */
public interface IPharmacistService extends IJpaService<Pharmacist, Long>
{
    PagedModel<PharmacistDTO> findByPharmacy(Long pharmacyId, Pageable pageable);

    /**
     * Lookup pharmacists of a pharmacy without an appointment at designated time period
     */
    PagedModel<PharmacistDTO> findFreeByPharmacy(FreePharmacistLookupRequest req, Pageable pageable);
    PagedModel<PharmacistDTO> findAllDTO(Pageable pageable);
    Optional<PharmacistDTO> findbyIdDTO(Long id);
    Optional<PharmacistDTO> updateDTO(Pharmacist pharmacist);
    Optional<Pharmacist> findByEmail(String email);
    Optional<PharmacistDTO> getDTO(Long id);
    PagedModel<PharmacistDTO> getAllDTO(Pageable pageable);
    PagedModel<PharmacistDTO> filteredSearch(Map<String, String> params, Pageable pageable);

}
