package com.schnabel.schnabel.drugs.repository;

import java.util.List;

import com.schnabel.schnabel.drugs.model.DrugPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * DrugPrice Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "drugprices", path = "drugprice")
public interface IDrugPriceRepository extends JpaRepository<DrugPrice, Long> {
    List<DrugPrice> findAllByWareHouseItemId(Long id);
}
