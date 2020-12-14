package com.schnabel.schnabel.pswregistration.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.schnabel.schnabel.pswregistration.model.SpecialOffer;
import com.schnabel.schnabel.pswregistration.repository.ISpecialOfferRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialOfferService implements ISpecialOfferService {

    private final ISpecialOfferRepository repository;

    @Autowired
    public SpecialOfferService(ISpecialOfferRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean add(SpecialOffer offer) {
        if (get(offer.getId()) != null)
            return false;
        repository.save(offer);
        return true;
    }

    @Override
    public boolean remove(int id) {
        SpecialOffer offer = get(id);
        if (offer == null)
            return false;
        repository.delete(offer);
        return true;
    }

    @Override
    public boolean update(SpecialOffer offer) {
        if (get(offer.getId()) == null)
            return false;
        repository.save(offer);
        return true;
    }

    @Override
    public SpecialOffer get(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<SpecialOffer> getAll() {
        return (List<SpecialOffer>) repository.findAll();
    }

    @Override
    public List<SpecialOffer> getOffersForTimePeriod(LocalDate from, LocalDate until)
    {
        return getAll().stream().filter(so -> so.isValidPeriod(from, until)).collect(Collectors.toList());
    }
    
}
