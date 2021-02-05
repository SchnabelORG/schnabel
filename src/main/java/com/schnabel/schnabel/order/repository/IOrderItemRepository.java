package com.schnabel.schnabel.order.repository;

import com.schnabel.schnabel.order.model.OrderItem;

import org.springframework.data.repository.CrudRepository;

public interface IOrderItemRepository extends CrudRepository<OrderItem, Long>
{
    
}
