package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.repository.IPharmacistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Pharmacist service implementation
 */
@Service
public class PharmacistService extends JpaService<Pharmacist, Long> implements IPharmacistService
{
    @Autowired
    public PharmacistService(IPharmacistRepository repository)
    {
        super(repository);
    }
}
