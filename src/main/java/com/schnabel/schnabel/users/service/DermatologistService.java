package com.schnabel.schnabel.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.repository.IDermatologistRepository;

import org.springframework.stereotype.Service;

/**
 * Dermatologist service implementation
 */
@Service
public class DermatologistService
    extends CrudService<Dermatologist, Long>
    implements IDermatologistService
{
    public DermatologistService(IDermatologistRepository repository)
    {
		super(repository);
	}

    @Override
    public Iterable<Dermatologist> getAllSpecificPharmacy(long id) 
    {
        List<Dermatologist> dermatologists = new ArrayList<>();
        for(Dermatologist dermatologist : getAll())
        {
            for(Pharmacy pharmacy : dermatologist.getPharmacies())
            {
                if(pharmacy.getId().equals(id))
                {
                    dermatologists.add(dermatologist);
                    break;
                }
            }
        }
        return dermatologists;
    }
}
