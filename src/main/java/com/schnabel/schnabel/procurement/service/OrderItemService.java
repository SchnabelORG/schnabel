package com.schnabel.schnabel.procurement.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.procurement.model.OrderItem;
import com.schnabel.schnabel.procurement.repository.IOrderItemRepository;

import org.springframework.stereotype.Service;

/**
 * Order service implementation
 */
@Service
public class OrderItemService extends JpaService<OrderItem, Long, IOrderItemRepository> implements IOrderItemService
{
    public OrderItemService(IOrderItemRepository repository)
    {
        super(repository);
    }
}
