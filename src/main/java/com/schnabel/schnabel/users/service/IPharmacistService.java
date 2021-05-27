package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.model.Pharmacist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Pharmacists service interface
 */
public interface IPharmacistService extends IJpaService<Pharmacist, Long>
{
    Page<Pharmacist> findByPharmacy(Long pharmacyId, Pageable pageable);
}
