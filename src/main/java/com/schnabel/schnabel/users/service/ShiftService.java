package com.schnabel.schnabel.users.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.users.model.Shift;
import com.schnabel.schnabel.users.repository.IShiftRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Shift service implementation
 */
@Service
public class ShiftService extends CrudService<Shift, Long> implements IShiftService 
{
    @Autowired
    public ShiftService(IShiftRepository repository)
    {
        super(repository);
    }

    /**
     * Get all dermatologists shifts
     * @return Iterable of Shifts
     */
    @Override
    public Iterable<Shift> getDermatologistAllShifts(Long employedId)
    {
        return StreamSupport.stream(getAll().spliterator(), false)
                .filter(s -> s.getEmployedUser().getId().equals(employedId)).collect(Collectors.toList());
    }

    /**
     * Get dermatologists shift for specific pharmacy
     * @return Shift
     */
    @Override
    public Shift getDermatologistShift(Long employedId, Long pharmacyId)
    {
        List<Shift> shifts = (List<Shift>) getAll();
        for(Shift shift : shifts)
        {
            if(shift.getEmployedUser().getId().equals(employedId) && shift.getPharmacy().getId().equals(pharmacyId))
            {
                return shift;
            }
        }
        return null;
    }
}
