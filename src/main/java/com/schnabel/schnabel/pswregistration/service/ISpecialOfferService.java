package com.schnabel.schnabel.pswregistration.service;

import java.time.LocalDate;
import java.util.List;

import com.schnabel.schnabel.pswregistration.model.SpecialOffer;

public interface ISpecialOfferService {
    public boolean add(SpecialOffer offer);
    public boolean remove(int id);
    public boolean update(SpecialOffer offer);
    public SpecialOffer get(int id);
    public List<SpecialOffer> getAll();
    public List<SpecialOffer> getOffersForTimePeriod(LocalDate from, LocalDate until);
}
