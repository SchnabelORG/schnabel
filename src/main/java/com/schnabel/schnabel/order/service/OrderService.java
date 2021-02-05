package com.schnabel.schnabel.order.service;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.order.model.Order;
import com.schnabel.schnabel.order.model.OrderItem;
import com.schnabel.schnabel.order.repository.IOrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of making order service
 */
@Service
public class OrderService extends CrudService<Order, Long> implements IOrderService {
    
    private final IOrderItemService orderItemService;
    
    @Autowired
    public OrderService(IOrderRepository repository, IOrderItemService orderItemService) {
        super(repository);
        this.orderItemService = orderItemService;
    }

    @Override
    public Iterable<Order> getExpiredOrders() {
        return StreamSupport.stream(getAll().spliterator(), false)
                .filter(o -> o.getDeadline().isBefore(LocalDate.now())).collect(Collectors.toList());
    }

    @Override
    public void addOrderItem(Order order, OrderItem orderItem) 
    {    
        order.addOrderItem(orderItem);
        update(order);
        this.orderItemService.add(orderItem);
    }
}
