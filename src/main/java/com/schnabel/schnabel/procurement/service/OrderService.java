package com.schnabel.schnabel.procurement.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.procurement.model.Order;
import com.schnabel.schnabel.procurement.repository.IOrderRepository;

import org.springframework.stereotype.Service;

/**
 * Order service implementation
 */
@Service
public class OrderService extends JpaService<Order, Long, IOrderRepository> implements IOrderService
{
    public OrderService(IOrderRepository repository)
    {
		super(repository);
	}
}

