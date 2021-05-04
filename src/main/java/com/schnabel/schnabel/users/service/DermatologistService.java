package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.repository.IDermatologistRepository;

import org.springframework.stereotype.Service;

/**
 * Dermatologist service implementation
 */
@Service
public class DermatologistService extends CrudService<Dermatologist, Long> implements IDermatologistService
{
    public DermatologistService(IDermatologistRepository repository)
    {
		super(repository);
	}
}


