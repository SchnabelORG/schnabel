package com.schnabel.schnabel.procurement.repository;

import java.util.List;

import com.schnabel.schnabel.procurement.model.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * OrderItem Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "orderitems", path = "orderitem")
public interface IOrderItemRepository extends JpaRepository<OrderItem, Long>
{
    List<OrderItem> findAllByOrderId(Long id);
}