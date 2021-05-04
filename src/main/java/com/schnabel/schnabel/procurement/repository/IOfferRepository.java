package com.schnabel.schnabel.procurement.repository;

import com.schnabel.schnabel.procurement.model.Offer;

import org.springframework.data.repository.CrudRepository;

/**
 * Offer CRUD repository interface
 */
public interface IOfferRepository extends CrudRepository<Offer, Long>
{
}
