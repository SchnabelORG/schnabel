package com.schnabel.schnabel.terms.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;
import com.schnabel.schnabel.terms.dto.TermDTO;
import com.schnabel.schnabel.terms.model.Term;
import com.schnabel.schnabel.terms.repository.ITermRepository;
import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.model.Shift;
import com.schnabel.schnabel.users.service.IDermatologistService;
import com.schnabel.schnabel.users.service.IPharmacistService;
import com.schnabel.schnabel.users.service.IShiftService;

import org.springframework.stereotype.Service;

/**
 * Term service implementation
 */
@Service
public class TermService extends CrudService<Term, Long> implements ITermService
{
    private final IDermatologistService dermatologistService;
    private final IPharmacyService pharmacyService;
    private final IShiftService shiftService;

    public TermService(ITermRepository repository, IDermatologistService dermatologistService, IPharmacyService pharmacyService, IShiftService shiftService)
    {
        super(repository);
        this.dermatologistService = dermatologistService;
        this.pharmacyService = pharmacyService;
        this.shiftService = shiftService;
    }

    @Override
    public boolean addTerms(Iterable<Term> terms)
    {
        for (Term term : terms) {
            if(add(term) == null)
            {
                return false;
            }            
        }
        return true;
    }

    @Override
    public Iterable<Term> createTerm(TermDTO termDTO)
    {
        List<Term> terms = new ArrayList<>();
		Dermatologist dermatologist = dermatologistService.get(termDTO.getEmployedId());
        Pharmacy pharmacy = pharmacyService.get(termDTO.getPharmacyId());
        Shift shiftForPharmacy = shiftService.getDermatologistShift(termDTO.getEmployedId(), termDTO.getPharmacyId());
            System.out.println("shift" + shiftForPharmacy.getId());
        boolean invalid = false;
        LocalDateTime currentTime = termDTO.getStartTime();
            System.out.println("startTime" + currentTime);

        while((currentTime.plusMinutes(termDTO.getDuration()).toLocalTime()).compareTo(shiftForPharmacy.getEndTime()) <= 0)
        {
            Term term = new Term(new Period(currentTime, currentTime.plusMinutes(termDTO.getDuration())), termDTO.getDuration(), termDTO.getPrice(), true, pharmacy, dermatologist, null);
            for(Shift shift : dermatologist.getShifts())
            {
                if(!shift.getId().equals(shiftForPharmacy.getId()) && (currentTime.toLocalTime().isBefore(shift.getStartTime()) || currentTime.plusMinutes(termDTO.getDuration()).toLocalTime().isAfter(shift.getEndTime())))
                {
                    invalid = true;
                    break;
                }
            }
            if(!invalid)
            {
                terms.add(term);
            }
            currentTime = currentTime.plusMinutes(termDTO.getDuration());
        }
        addTerms(terms);
        return terms;
    }

}
