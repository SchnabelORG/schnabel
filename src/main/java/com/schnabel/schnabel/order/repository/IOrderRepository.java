package com.schnabel.schnabel.order.repository;

import com.schnabel.schnabel.order.model.Order;

import org.springframework.data.repository.CrudRepository;

/**
 * Making order repository interface
 */
public interface IOrderRepository extends CrudRepository<Order, Long>
{
    
}
