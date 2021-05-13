package com.schnabel.schnabel.promotion.repository;

import com.schnabel.schnabel.promotion.model.Promotion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Promotion Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "promotions", path = "promotion")
public interface IPromotionRepository extends JpaRepository<Promotion, Long>
{
}
