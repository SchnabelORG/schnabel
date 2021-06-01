package com.schnabel.schnabel.procurement.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.procurement.dto.OrderDTO;
import com.schnabel.schnabel.procurement.dto.OrderDTOAssembler;
import com.schnabel.schnabel.procurement.model.Order;
import com.schnabel.schnabel.procurement.model.OrderItem;
import com.schnabel.schnabel.procurement.repository.IOrderRepository;
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
 * Order service implementation
 */
@Service
public class OrderService extends JpaService<Order, Long, IOrderRepository> implements IOrderService
{
    private final OrderDTOAssembler orderDTOAssembler;
    private final PagedResourcesAssembler<Order> orderPagedResourcesAssembler;
    private final IPharmacyAdminService pharmacyAdminService;

    public OrderService(IOrderRepository repository, OrderDTOAssembler orderDTOAssembler, PagedResourcesAssembler<Order> orderPagedResourcesAssembler, IPharmacyAdminService pharmacyAdminService)
    {
		super(repository);
        this.orderDTOAssembler = orderDTOAssembler;
        this.orderPagedResourcesAssembler = orderPagedResourcesAssembler;
        this.pharmacyAdminService = pharmacyAdminService;
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
    public PagedModel<OrderDTO> getNewOrders(Pageable pageable, Long id) {
        Page<Order> orders = repository.findByDeadlineAfterAndSupplierId(pageable, LocalDate.now(), id);
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
    public boolean createNewOrder(String description, LocalDate deadline, List<OrderItem> orderItems, String email) 
    {
        Order newOrder = new Order(description, deadline, orderItems, pharmacyAdminService.findByEmail(email).get(), pharmacyAdminService.findByEmail(email).get().getPharmacy());
        Optional<Order> order = add(newOrder);
        if(order.isPresent())
        {
            return true;
        }
        return false;
    }
}


