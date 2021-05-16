package com.schnabel.schnabel.pharmacies.repository;

import com.schnabel.schnabel.pharmacies.model.ERecipeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *  eRecipe item Jpa repository interface
 */

@RepositoryRestResource(collectionResourceRel = "recipe_items", path = "recipeitem")
public interface IERecipeItemRepository extends JpaRepository<ERecipeItem, Long> {
}
