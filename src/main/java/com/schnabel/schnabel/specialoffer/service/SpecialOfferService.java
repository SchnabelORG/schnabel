package com.schnabel.schnabel.specialoffer.service;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.specialoffer.model.SpecialOffer;
import com.schnabel.schnabel.specialoffer.repository.ISpecialOfferRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialOfferService extends CrudService<SpecialOffer, Integer> implements ISpecialOfferService 
{

    @Autowired
    public SpecialOfferService(ISpecialOfferRepository repository)
    {
        super(repository);
    }

    @Override
    public Iterable<SpecialOffer> getOffersForTimePeriod(LocalDate from, LocalDate until)
    {
        // TODO(Jovan): Compatible with iterable?
        return StreamSupport.stream(getAll().spliterator(), false)
            .filter(so -> so.isValidPeriod(from, until)).collect(Collectors.toList());
    }
    
}
