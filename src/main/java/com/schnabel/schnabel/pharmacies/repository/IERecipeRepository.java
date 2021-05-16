package com.schnabel.schnabel.pharmacies.repository;

import com.schnabel.schnabel.pharmacies.model.ERecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *  eRecipe Jpa repository interface
 */

@RepositoryRestResource(collectionResourceRel = "recipes", path = "recipe")
public interface IERecipeRepository extends JpaRepository<ERecipe, Long> {
}
