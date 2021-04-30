package com.schnabel.schnabel.procurement.repository;

import com.schnabel.schnabel.procurement.model.OrderItem;

import org.springframework.data.repository.CrudRepository;

/**
 * OrderItem CRUD repository interface
 */
public interface IOrderItemRepository extends CrudRepository<OrderItem, Long>
{
}