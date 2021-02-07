package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.pharmacies.model.Term;
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

	@Override
    public boolean addTerm(Pharmacist pharmacist, Term term)
    {
        return pharmacist.addTerm(term)
            && update(pharmacist);
	}

	@Override
    public boolean removeTerm(Pharmacist pharmacist, Term term)
    {
        return pharmacist.removeTerm(term)
            && update(pharmacist);
	}

	@Override
    public boolean checkIfFree(Long id, Period period)
    {
        Pharmacist pharmacist = get(id);
        if(pharmacist == null)
        {
            return false;
        }
        for (Term term : pharmacist.getTerms())
        {
            if(period.isOverlapping(term.getPeriod()))
            {
                return false;
            }
        }
        return true;
	}
}
