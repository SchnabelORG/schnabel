package com.schnabel.schnabel.pswregistration.service;

import java.time.LocalDate;

import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.pswregistration.model.SpecialOffer;

public interface ISpecialOfferService extends ICrudService<SpecialOffer, Integer>
{
    public Iterable<SpecialOffer> getOffersForTimePeriod(LocalDate from, LocalDate until);
}
