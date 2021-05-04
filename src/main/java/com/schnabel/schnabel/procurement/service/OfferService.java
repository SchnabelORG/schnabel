package com.schnabel.schnabel.procurement.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.procurement.model.Offer;
import com.schnabel.schnabel.procurement.repository.IOfferRepository;

import org.springframework.stereotype.Service;

/**
 * Offer service implementation
 */
@Service
public class OfferService extends CrudService<Offer, Long> implements IOfferService
{
    public OfferService(IOfferRepository repository)
    {
		super(repository);
	}
}
