package com.schnabel.schnabel.procurement.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.procurement.dto.OrderDTO;
import com.schnabel.schnabel.procurement.dto.OrderDTOAssembler;
import com.schnabel.schnabel.procurement.model.Order;
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
 * Order service implementation
 */
@Service
public class OrderService extends JpaService<Order, Long, IOrderRepository> implements IOrderService
{
    private final OrderDTOAssembler orderDTOAssembler;
    private final PagedResourcesAssembler<Order> orderPagedResourcesAssembler;

    public OrderService(IOrderRepository repository, OrderDTOAssembler orderDTOAssembler, PagedResourcesAssembler<Order> orderPagedResourcesAssembler)
    {
		super(repository);
        this.orderDTOAssembler = orderDTOAssembler;
        this.orderPagedResourcesAssembler = orderPagedResourcesAssembler;
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
}


