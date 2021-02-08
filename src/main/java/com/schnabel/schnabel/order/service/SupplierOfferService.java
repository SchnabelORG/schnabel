package com.schnabel.schnabel.order.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.order.model.SupplierOffer;
import org.springframework.data.repository.CrudRepository;

public class SupplierOfferService extends CrudService<SupplierOffer, Long> implements ISupplierOfferService
{
    public SupplierOfferService(CrudRepository<SupplierOffer, Long> repository) {
        super(repository);
    }
}
