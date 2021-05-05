package com.schnabel.schnabel.procurement.repository;

import com.schnabel.schnabel.procurement.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Order Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "orders", path = "order")
public interface IOrderRepository extends JpaRepository<Order, Long>
{
}
