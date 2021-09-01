package com.schnabel.schnabel.procurement.service;

import com.schnabel.schnabel.drugs.service.IDrugService;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.procurement.dto.OrderDTO;
import com.schnabel.schnabel.procurement.dto.OrderDTOAssembler;
import com.schnabel.schnabel.procurement.dto.OrderItemRequest;
import com.schnabel.schnabel.procurement.dto.OrderUpdateRequest;
import com.schnabel.schnabel.procurement.model.Order;
import com.schnabel.schnabel.procurement.model.OrderItem;
import com.schnabel.schnabel.procurement.model.OrderStatus;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Order service implementation
 */
@Service
public class OrderService extends JpaService<Order, Long, IOrderRepository> implements IOrderService
{
    private final OrderDTOAssembler orderDTOAssembler;
    private final PagedResourcesAssembler<Order> orderPagedResourcesAssembler;
    private final IPharmacyAdminService pharmacyAdminService;
    private final IDrugService drugService;
    private final IOrderItemService orderItemService;
    private final IOfferService offerService;
    private final ISupplierRepository supplierRepository;

    public OrderService(IOrderRepository repository, OrderDTOAssembler orderDTOAssembler, PagedResourcesAssembler<Order> orderPagedResourcesAssembler, IPharmacyAdminService pharmacyAdminService, IDrugService drugService, IOrderItemService orderItemService, IOfferService offerService, ISupplierRepository supplierRepository)
    {
		super(repository);
        this.orderDTOAssembler = orderDTOAssembler;
        this.orderPagedResourcesAssembler = orderPagedResourcesAssembler;
        this.pharmacyAdminService = pharmacyAdminService;
        this.drugService = drugService;
        this.orderItemService = orderItemService;
        this.offerService = offerService;
        this.supplierRepository = supplierRepository;
    }

    @Override
    @Transactional
    public Optional<OrderDTO> getDTO(Long id) {
        return get(id).map(orderDTOAssembler::toModel);
    }

    @Override
    @Transactional
    public PagedModel<OrderDTO> getAllDTO(Pageable pageable) {
        Page<Order> orders = repository.findAll(pageable);
        return orderPagedResourcesAssembler.toModel(orders, orderDTOAssembler);
    }


    @Override
    @Transactional
    public PagedModel<OrderDTO> getNonExpired(Pageable pageable) {
        Page<Order> orders = repository.findByDeadlineAfter(pageable, LocalDate.now());
        return orderPagedResourcesAssembler.toModel(orders, orderDTOAssembler);
    }

    @Override
    @Transactional
    public PagedModel<OrderDTO> getNewOrders(Pageable pageable, String email) {
        Optional<Supplier> supplier = supplierRepository.findByEmail(email);
        Page<Order> orders = repository.findByDeadlineAfterAndSupplierId(pageable, LocalDate.now(), supplier.get().getId());
        return orderPagedResourcesAssembler.toModel(orders, orderDTOAssembler);
    }
    
    @Override
    @Transactional
    public PagedModel<OrderDTO> getCreatedOrdersByPharmacyId(Long pharmacyId, Pageable pageable)
    {
        Page<Order> orders = repository.findCreatedOrdersByPharmacyId(pharmacyId, pageable);
        return orderPagedResourcesAssembler.toModel(orders, orderDTOAssembler);
    }

    @Override
    public boolean createNewOrder(String description, LocalDate deadline, List<OrderItemRequest> orderItemRequests, String email) 
    {
        Order newOrder = new Order(description, deadline, pharmacyAdminService.findByEmail(email).get(), pharmacyAdminService.findByEmail(email).get().getPharmacy(), OrderStatus.CREATED);
        Optional<Order> order = add(newOrder);
        if(order.isPresent())
        {
            return addOrderItemsToOrder(orderItemRequests, order.get());
        }
        return false;
    }

    private boolean addOrderItemsToOrder(List<OrderItemRequest> orderItemRequests, Order order)
    {
        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        for (OrderItemRequest orderItemRequest : orderItemRequests) 
        {
            OrderItem newOrderItem = new OrderItem(orderItemRequest.getQuantity(), drugService.get(orderItemRequest.getDrugId()).get(), order);
            Optional<OrderItem> orderItem = orderItemService.add(newOrderItem);
            if(!orderItem.isPresent())
            {
                return false;
            }
            orderItems.add(orderItem.get());
        }
        order.setOrderItems(orderItems);
        return update(order);
    }

    @Override
    public boolean deleteOrder(Pageable pageable, Long orderId)
    {
        if(!offerService.checkEmptyOrderOfferList(orderId, pageable))
        {
            return false;
        }       
        List<OrderItem> orderItems = orderItemService.findAllByOrderId(orderId);
        for (OrderItem orderItem : orderItems) 
        {
            if(orderItemService.remove(orderItem.getId()) == false)
            {
                return false;
            }
        }        
        return remove(orderId);
    }

    @Override
    public boolean updateOrder(OrderUpdateRequest orderUpdateRequest, Pageable pageable)
    {
        Optional<Order> order = get(orderUpdateRequest.getId());
        if(!order.isPresent()) 
        {
            return false;
        }
        if(!offerService.checkEmptyOrderOfferList(order.get().getId(), pageable))
        {
            return false;
        }
        order.get().setDescription(orderUpdateRequest.getDescription());
        order.get().setDeadline(orderUpdateRequest.getDeadline());
        return update(order.get());
    }
}