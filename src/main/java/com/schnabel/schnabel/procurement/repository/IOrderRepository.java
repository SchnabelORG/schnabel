package com.schnabel.schnabel.procurement.repository;

import com.schnabel.schnabel.procurement.model.Order;

import org.springframework.data.repository.CrudRepository;

/**
 * Order CRUD repository interface
 */
public interface IOrderRepository extends CrudRepository<Order, Long>
{
}
