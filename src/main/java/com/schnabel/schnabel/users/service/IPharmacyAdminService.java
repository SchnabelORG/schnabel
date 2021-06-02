package com.schnabel.schnabel.users.service;

import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.model.PharmacyAdmin;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

/**
 * PharmacyAdmin service interface
 */
public interface IPharmacyAdminService extends IJpaService<PharmacyAdmin, Long>
{
    Optional<PharmacyAdmin> findByEmail(String email);
    PagedModel<DermatologistDTO> getAllDermatologists(String email, Pageable pageable);
    PagedModel<PharmacistDTO> getAllPharmacists(String email, Pageable pageable);
    PagedModel<WareHouseItemDTO> getAllDrugs(String email, Pageable pageable);
}
