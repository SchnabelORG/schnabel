package com.schnabel.schnabel.appointment.repository;


import com.schnabel.schnabel.appointment.model.RecommendedMed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * RecommendedMed Jpa repository interface
 */

@RepositoryRestResource(collectionResourceRel = "recommended_meds", path = "recommendedmed")
public interface IRecommendedMedRepository extends JpaRepository<RecommendedMed, Long> {
}
