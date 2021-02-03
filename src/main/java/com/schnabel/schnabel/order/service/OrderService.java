package com.schnabel.schnabel.order.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.order.model.Order;
import com.schnabel.schnabel.order.repository.IOrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends CrudService<Order, Integer> implements IOrderService
{
    @Autowired
    public OrderService(IOrderRepository repository)
    {
        super(repository);
    }
}
