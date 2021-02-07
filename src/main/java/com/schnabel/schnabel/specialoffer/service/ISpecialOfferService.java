package com.schnabel.schnabel.specialoffer.service;

import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.specialoffer.model.SpecialOffer;

public interface ISpecialOfferService extends ICrudService<SpecialOffer, Long>
{
    public Iterable<SpecialOffer> getOffersForTimePeriod(Period period);
}
