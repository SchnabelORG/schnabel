package com.schnabel.schnabel.order.service;
import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.order.model.Order;

/**
 * Making order service interface
 */
public interface IOrderService extends ICrudService<Order, Integer>
{
    public Iterable<Order> getExpiredOrders();
}
