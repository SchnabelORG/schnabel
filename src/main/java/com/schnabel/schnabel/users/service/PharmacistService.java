package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.repository.IPharmacistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PharmacistService
    extends CrudService<Pharmacist, Long>
    implements IPharmacistService
{
    @Autowired
    public PharmacistService(IPharmacistRepository repository)
    {
        super(repository);
    }
}
