package com.schnabel.schnabel.users.service;

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
}
