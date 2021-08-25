package com.schnabel.schnabel.users.service;

import java.util.Map;
import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.model.Dermatologist;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

/**
 * Dermatologist Service interface
 */
public interface IDermatologistService extends IJpaService<Dermatologist, Long>
{
    Optional<DermatologistDTO> getDTO(Long id);
    PagedModel<DermatologistDTO> getAllDTO(Pageable pageable);
    boolean registerDermatologist(String name, String surname, String email, String password, Address address);
    Optional<Dermatologist> findByEmail(String email);
    PagedModel<DermatologistDTO> findAllByPharmacy(Long pharmacyId, Pageable pageable);
    PagedModel<DermatologistDTO> filteredSearch(Map<String, String> params, Pageable pageable);
    PagedModel<DermatologistDTO> filteredSearchPharmacyAdmin(Map<String, String> params, Long pharmacyId, Pageable pageable);
    PagedModel<DermatologistDTO> findAllDermatologistNotInPharmacy(Long pharmacyId, Pageable pageable);
}
