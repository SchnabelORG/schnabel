package com.schnabel.schnabel.procurement.service;

import com.schnabel.schnabel.email.service.IMailService;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;
import com.schnabel.schnabel.pharmacies.service.IWareHouseItemService;
import com.schnabel.schnabel.procurement.dto.OfferDTO;
import com.schnabel.schnabel.procurement.dto.OfferDTOAssembler;
import com.schnabel.schnabel.procurement.model.Offer;
import com.schnabel.schnabel.procurement.model.OfferStatus;
import com.schnabel.schnabel.procurement.model.Order;
import com.schnabel.schnabel.procurement.model.OrderItem;
import com.schnabel.schnabel.procurement.model.OrderStatus;
import com.schnabel.schnabel.procurement.repository.IOfferRepository;

import com.schnabel.schnabel.procurement.repository.IOrderRepository;
import com.schnabel.schnabel.users.model.Supplier;
import com.schnabel.schnabel.users.repository.ISupplierRepository;
import com.schnabel.schnabel.users.service.IPharmacyAdminService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
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
    private final IPharmacyAdminService pharmacyAdminService;
    private final IMailService mailService;
    private final IWareHouseItemService wareHouseItemService;
    private final IOrderItemService orderItemService;
    private final ISupplierRepository supplierRepository;

    public OfferService(IOfferRepository repository, OfferDTOAssembler offerDTOAssembler, PagedResourcesAssembler<Offer> offerPagedResourcesAssembler, IOrderRepository orderRepository, IPharmacyAdminService pharmacyAdminService, IWareHouseItemService wareHouseItemService, IOrderItemService orderItemService, IMailService mailService, ISupplierRepository supplierRepository)
    {
		super(repository);
        this.offerDTOAssembler = offerDTOAssembler;
        this.offerPagedResourcesAssembler = offerPagedResourcesAssembler;
        this.orderRepository = orderRepository;
        this.pharmacyAdminService = pharmacyAdminService;
        this.wareHouseItemService = wareHouseItemService;
        this.orderItemService = orderItemService;
        this.mailService = mailService;
        this.supplierRepository = supplierRepository;
    }

    @Override
    @Transactional
    public PagedModel<OfferDTO> findBySupplier(Pageable pageable, String email) {
        Optional<Supplier> suppler =  supplierRepository.findByEmail(email);
        Page<Offer> offers = repository.findBySupplierId(pageable, suppler.get().getId());
        return offerPagedResourcesAssembler.toModel(offers, offerDTOAssembler);
    }


    @Override
    @Transactional
    public boolean createOffer(int price, LocalDate dateOfDelivery, long orderId, String email) {
        Offer newOffer = new Offer(price, dateOfDelivery);
        Order order = orderRepository.findById(orderId).get();
        if(order==null)
            return false;
        if(order.getDeadline().isBefore(dateOfDelivery))
            return false;
        newOffer.setOrder(order);
        newOffer.setOfferStatus(OfferStatus.CREATED);
        Optional<Supplier> suppler =  supplierRepository.findByEmail(email);
        if(!suppler.isPresent())
            return false;
        newOffer.setSupplier(suppler.get());
        Optional<Offer> offer = add(newOffer);
        if(offer.isPresent())
            return true;
        return false;
    }

    @Override
    @Transactional
    public PagedModel<OfferDTO> findByOrder(Pageable pageable, Long id) {
        Page<Offer> offers = repository.findByOrderId(pageable, id);
        return offerPagedResourcesAssembler.toModel(offers, offerDTOAssembler);
    }

    @Override
    @Transactional
    public boolean updateOffer(Long id, int price, LocalDate dateOfDelivery) {
        try {
            Offer offer = get(id).get();
            if (offer != null) {
                if (offer.getOrder().getDeadline().isBefore(LocalDate.now()))
                    return false;
                offer.setPrice(price);
                offer.setDateOfDelivery(dateOfDelivery);
                return update(offer);
            }
            return false;
        }catch (Exception e)
        {
            return false;
        }
    }

    @Override
    @Transactional
    public Optional<OfferDTO> getDTO(Long id) {
        return get(id).map(offerDTOAssembler::toModel);
    }

    @Override
    @Transactional
    public PagedModel<OfferDTO> getAllDTO(Pageable pageable) {
        Page<Offer> offers = repository.findAll(pageable);
        return offerPagedResourcesAssembler.toModel(offers, offerDTOAssembler);
    }

    @Override
    @Transactional
    public boolean acceptOffer(Long offerId, String email, Pageable pageable) {
        Offer offer = get(offerId).get();
        Order order = offer.getOrder();
        if(order.getPharmacyAdmin().getId() != pharmacyAdminService.findByEmail(email).get().getId() || order.getDeadline().isAfter(LocalDate.now()))
        {
            return false;
        }

        offer.setOfferStatus(OfferStatus.ACCEPTED);
        mailService.sendOrderClosingMail(offer.getSupplier().getEmail(), "Your offer is ACCEPTED for order -> id: " + order.getId() + " description: " + order.getDescription());
        for (Offer o : order.getOffers()) 
        {
            if(o.getId() != offerId)
            {
                o.setOfferStatus(OfferStatus.REJECTED);
                mailService.sendOrderClosingMail(o.getSupplier().getEmail(), "Your offer is REJECTED for order -> id: " + order.getId() + " description: " + order.getDescription());
                update(o);
            }
        }
        order.setOrderStatus(OrderStatus.CLOSED);
        update(offer);
        return updateWareHouseItems(offer, order, pageable);
    }

    @Override
    public boolean checkEmptyOrderOfferList(Long orderId, Pageable pageable)
    {
        Page<Offer> offers = repository.findByOrderId(pageable, orderId);
        return offers.isEmpty();
    }

    @Override
    public PagedModel<OfferDTO> findBySupplierFiltered(Pageable pageable, String email, OfferStatus offerStatus) {
        Optional<Supplier> suppler =  supplierRepository.findByEmail(email);
        Page<Offer> offers = repository.findBySupplierIdAndOfferStatus(pageable, suppler.get().getId(), offerStatus);
        return offerPagedResourcesAssembler.toModel(offers, offerDTOAssembler);
    }

    private boolean updateWareHouseItems(Offer offer, Order order, Pageable pageable)
    {
        List<OrderItem> orderItems = orderItemService.findAllByOrderId(order.getId());
        for(OrderItem orderItem : orderItems)
        {
            Optional<WareHouseItem> wareHouseItem = wareHouseItemService.findWareHouseItemByPharmacyAndDrugId(orderItem.getDrug().getId(), order.getPharmacy().getId());
            if(!wareHouseItem.isPresent())
            {
                return false;
            }
            wareHouseItem.get().setQuantity(wareHouseItem.get().getQuantity() + orderItem.getQuantity());
            wareHouseItemService.update(wareHouseItem.get());
        }
        return true;
    }

}
