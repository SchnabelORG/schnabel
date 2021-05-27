package com.schnabel.schnabel.users.service;

import java.time.LocalTime;
import java.util.Optional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.users.model.MedicalEmployee;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.model.Shift;
import com.schnabel.schnabel.users.repository.IPharmacyAdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PharmacyAdminService extends JpaService<PharmacyAdmin, Long, IPharmacyAdminRepository> implements IPharmacyAdminService
{
    private final IShiftService shiftService;

    @Autowired
    public PharmacyAdminService(IPharmacyAdminRepository pharmacyAdminRepository, IShiftService shiftService)
    {
        super(pharmacyAdminRepository);
        this.shiftService = shiftService;
    }

    @Override
    public boolean defineShift(LocalTime startTime, LocalTime endTime, MedicalEmployee medicalEmployee)
    {
        Shift newShift = new Shift(startTime, endTime, medicalEmployee);
        Optional<Shift> shift = shiftService.add(newShift);
        if(shift.isPresent())
        {
            return true;
        }
        return false;
    }
}