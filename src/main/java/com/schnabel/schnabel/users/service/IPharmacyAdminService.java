package com.schnabel.schnabel.users.service;

import java.time.LocalTime;
import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.model.MedicalEmployee;
import com.schnabel.schnabel.users.model.PharmacyAdmin;

/**
 * PharmacyAdmin service interface
 */
public interface IPharmacyAdminService extends IJpaService<PharmacyAdmin, Long>
{
    boolean defineShift(LocalTime startTime, LocalTime endTime, MedicalEmployee medicalEmployee);
    Optional<PharmacyAdmin> findByEmail(String email);
}
