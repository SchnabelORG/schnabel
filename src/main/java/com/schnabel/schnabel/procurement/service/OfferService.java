package com.schnabel.schnabel.procurement.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.procurement.dto.OfferDTO;
import com.schnabel.schnabel.procurement.dto.OfferDTOAssembler;
import com.schnabel.schnabel.procurement.model.Offer;
import com.schnabel.schnabel.procurement.model.Order;
import com.schnabel.schnabel.procurement.repository.IOfferRepository;

import com.schnabel.schnabel.procurement.repository.IOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Offer service implementation
 */
@Service
public class OfferService extends JpaService<Offer, Long, IOfferRepository> implements IOfferService
{
    private final OfferDTOAssembler offerDTOAssembler;
    private final PagedResourcesAssembler<Offer> offerPagedResourcesAssembler;
    private final IOrderRepository orderRepository;

    public OfferService(IOfferRepository repository, OfferDTOAssembler offerDTOAssembler, PagedResourcesAssembler<Offer> offerPagedResourcesAssembler, IOrderRepository orderRepository)
    {
		super(repository);
        this.offerDTOAssembler = offerDTOAssembler;
        this.offerPagedResourcesAssembler = offerPagedResourcesAssembler;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public PagedModel<OfferDTO> findBySupplier(Pageable pageable, Long id) {
        Page<Offer> offers = repository.findBySupplierId(pageable, id);
        return offerPagedResourcesAssembler.toModel(offers, offerDTOAssembler);
    }

    @Override
    public boolean createOffer(int price, LocalDate dateOfDelivery, long orderId) {
        Offer newOffer = new Offer(price, dateOfDelivery);
        Order order = orderRepository.findById(orderId).get();
        if(order==null)
            return false;
        newOffer.setOrder(order);
        Optional<Offer> offer = add(newOffer);
        if(offer.isPresent())
            return true;
        return false;
    }
}
