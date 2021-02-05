package com.schnabel.schnabel.order.service;

import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.order.model.OrderItem;

public interface IOrderItemService extends ICrudService<OrderItem, Integer>
{
    public boolean addOrderItems(Iterable<OrderItem> orderItems);
}
