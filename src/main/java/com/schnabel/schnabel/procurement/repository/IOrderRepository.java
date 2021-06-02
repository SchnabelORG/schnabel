package com.schnabel.schnabel.procurement.repository;

import com.schnabel.schnabel.procurement.model.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;

/**
 * Order Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "orders", path = "order")
public interface IOrderRepository extends JpaRepository<Order, Long>
{
    Page<Order> findByDeadlineAfter(Pageable pageable, LocalDate date);

    @Query("SELECT o FROM Order o WHERE deadline > ?1 and o.id NOT IN (SELECT offer.order.id FROM Offer offer WHERE offer.supplier.id = ?2 )")
    Page<Order> findByDeadlineAfterAndSupplierId(Pageable pageable, LocalDate date, Long id);

    @Query("SELECT o FROM Order o WHERE pharmacy_id = :pharmacy_id AND order_status = 'CREATED'")
    Page<Order> findCreatedOrdersByPharmacyId(@Param("pharmacy_id") Long pharmacyId, Pageable pageable);
}
