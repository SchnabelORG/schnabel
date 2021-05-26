package com.schnabel.schnabel.drugs.repository;

import com.schnabel.schnabel.drugs.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "ingredients", path = "ingredient")
public interface IIngredientRepository extends JpaRepository<Ingredient, Long> {

}
