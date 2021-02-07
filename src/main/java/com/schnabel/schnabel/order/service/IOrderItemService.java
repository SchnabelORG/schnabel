package com.schnabel.schnabel.order.service;

import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.order.model.OrderItem;

public interface IOrderItemService extends ICrudService<OrderItem, Long>
{
    public Iterable<OrderItem> addOrderItems(Iterable<OrderItem> orderItems);
}