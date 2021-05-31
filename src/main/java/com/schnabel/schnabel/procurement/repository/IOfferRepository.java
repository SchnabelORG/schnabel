package com.schnabel.schnabel.procurement.repository;

import com.schnabel.schnabel.procurement.model.Offer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Offer Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "offers", path = "offer")
public interface IOfferRepository extends JpaRepository<Offer, Long>
{
    Page<Offer> findBySupplierId(Pageable pageable, Long id);
    Page<Offer> findByOrderId(Pageable pageable, Long id);
}