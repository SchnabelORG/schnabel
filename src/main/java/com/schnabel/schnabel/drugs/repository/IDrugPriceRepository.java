package com.schnabel.schnabel.drugs.repository;

import com.schnabel.schnabel.drugs.model.DrugPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * DrugPrice Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "drugprice", path = "pricedrug")
public interface IDrugPriceRepository extends JpaRepository<DrugPrice, Long> {
}
