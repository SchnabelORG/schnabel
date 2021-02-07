package com.schnabel.schnabel.order.service;

import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.order.model.Order;
import com.schnabel.schnabel.order.model.OrderItem;

/**
 * Making order service interface
 */
public interface IOrderService extends ICrudService<Order, Long>
{
    public Iterable<Order> getExpiredOrders();
    public void addOrderItem(Order order, OrderItem orderItem);
}
