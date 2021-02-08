package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.users.model.Shift;

/**
 * Shift service interface
 */
public interface IShiftService extends ICrudService<Shift, Long> 
{
    public Iterable<Shift> getDermatologistAllShifts(Long employedId);
    public Shift getDermatologistShift(Long employedId, Long pharmacyId);
}
