package com.schnabel.schnabel.order.service;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.order.model.Order;
import com.schnabel.schnabel.order.repository.IOrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of making order service
 */
@Service
public class OrderService extends CrudService<Order, Integer> implements IOrderService
{
    @Autowired
    public OrderService(IOrderRepository repository)
    {
        super(repository);
    }
    
    @Override
    public boolean add(Order order)
    {
        if(get(order.getId()) == null)
        {
            repository.save(order);
            return true;
        }
        return false;
    }

    @Override 
    public Iterable<Order> getExpiredOrders() 
    {
        return StreamSupport.stream(getAll().spliterator(), false)
            .filter(o -> o.getDeadline().isBefore(LocalDate.now())).collect(Collectors.toList());
    }
}
