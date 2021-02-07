package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.repository.IPharmacistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Pharmacist service implementation
 */
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

    /**
     * Get all pharmacists for specific pharmacy
     * @return Iterable of Pharmacist
     */
    @Override
    public Iterable<Pharmacist> getAllSpecificPharmacy(long id) 
    {
        List<Pharmacist> pharmacists = new ArrayList<>();
        for(Pharmacist pharmacist : getAll())
        {
           if(pharmacist.getPharmacy().getId().equals(id))
            {
                pharmacists.add(pharmacist);
                break;
            }
        }
        return pharmacists;
    }
}
