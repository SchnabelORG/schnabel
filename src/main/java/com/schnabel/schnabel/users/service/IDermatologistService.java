package com.schnabel.schnabel.users.service;

import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.model.Dermatologist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

/**
 * Dermatologist Service interface
 */
public interface IDermatologistService extends IJpaService<Dermatologist, Long>
{
    Optional<DermatologistDTO> getDTO(Long id);
    PagedModel<DermatologistDTO> getAllDTO(Pageable pageable);
    Optional<Dermatologist> findByEmail(String email);
    Page<Dermatologist> findAllByPharmacy(Long pharmacyId, Pageable pageable);
    //Page<Dermatologist> findByPharmaciesId(Long pharmacyId, Pageable pageable);

}
