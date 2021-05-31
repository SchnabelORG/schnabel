package com.schnabel.schnabel.users.service;

import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.model.PharmacyAdmin;

/**
 * PharmacyAdmin service interface
 */
public interface IPharmacyAdminService extends IJpaService<PharmacyAdmin, Long>
{
    Optional<PharmacyAdmin> findByEmail(String email);
}
