package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.dto.PharmacyAdminDTO;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;

/**
 * PharmacyAdmin service interface
 */
public interface IPharmacyAdminService extends IJpaService<PharmacyAdmin, Long>
{
    Optional<PharmacyAdminDTO> getDTO(Long id);
    PagedModel<PharmacyAdminDTO> getAllDTO(Pageable pageable);
    PagedModel<PharmacyAdminDTO> findByPharmacy(Pageable pageable, Long pharmacyId);
    PagedModel<PharmacyAdminDTO> findWithoutPharmacy(Pageable pageable);
    boolean assignPharmacyAdmin(Long pharmacyAdminId, Long pharmacyId);
}
