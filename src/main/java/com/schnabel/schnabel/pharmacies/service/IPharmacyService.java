package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.pharmacies.dto.PharmacyCreationDTO;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;

public interface IPharmacyService extends IJpaService<Pharmacy, Long>
{
    boolean registerPharmacy(PharmacyCreationDTO creationDTO);
    Optional<PharmacyDTO> getDTO(Long id);
    PagedModel<PharmacyDTO> getAllDTO(Pageable pageable);

}
