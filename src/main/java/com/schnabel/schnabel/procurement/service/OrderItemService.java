package com.schnabel.schnabel.procurement.service;

import java.util.List;

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

    @Override
    public List<OrderItem> findAllByOrderId(Long id)
    {
        return repository.findAllByOrderId(id);    
    }    
}
