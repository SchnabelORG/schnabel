package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.model.Pharmacist;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;

/**
 * Pharmacists service interface
 */
public interface IPharmacistService extends IJpaService<Pharmacist, Long>
{
    Optional<PharmacistDTO> getDTO(Long id);
    PagedModel<PharmacistDTO> getAllDTO(Pageable pageable);
}
