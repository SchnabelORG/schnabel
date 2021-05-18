package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * PharmacyAdmin service interface
 */
public interface IPharmacyAdminService extends IJpaService<PharmacyAdmin, Long>
{
    Page<PharmacyAdmin> findByPharmacy(Pageable pageable, Long pharmacyId);
    Page<PharmacyAdmin> findWithoutPharmacy(Pageable pageable);
    boolean assignPharmacyAdmin(Long pharmacyAdminId, Long pharmacyId);
}
