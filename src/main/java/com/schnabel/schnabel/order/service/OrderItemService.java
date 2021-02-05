package com.schnabel.schnabel.order.service;

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
    public boolean addOrderItems(Iterable<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) 
        {
            if (add(orderItem) == null)
            {
                return false;
            }

        }
        return true;
    }
}
