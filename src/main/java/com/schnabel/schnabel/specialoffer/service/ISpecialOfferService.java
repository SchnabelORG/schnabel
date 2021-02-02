package com.schnabel.schnabel.specialoffer.service;

import java.time.LocalDate;

import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.specialoffer.model.SpecialOffer;

public interface ISpecialOfferService extends ICrudService<SpecialOffer, Integer>
{
    public Iterable<SpecialOffer> getOffersForTimePeriod(LocalDate from, LocalDate until);
}
