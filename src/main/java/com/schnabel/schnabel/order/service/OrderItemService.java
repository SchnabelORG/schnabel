package com.schnabel.schnabel.order.service;

import java.util.ArrayList;
import java.util.List;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.order.model.OrderItem;
import com.schnabel.schnabel.order.repository.IOrderItemRepository;

import org.springframework.stereotype.Service;

@Service
public class OrderItemService extends CrudService<OrderItem, Long>
implements IOrderItemService
{
    public OrderItemService(IOrderItemRepository repository) {
        super(repository);
    }

    @Override
    public Iterable<OrderItem> addOrderItems(Iterable<OrderItem> orderItems) {
        
        List<OrderItem> ret = new ArrayList<>();
        for (OrderItem orderItem : orderItems) 
        {
            OrderItem oi = add(orderItem);
            ret.add(oi);
        }
        return ret;
    }
}
