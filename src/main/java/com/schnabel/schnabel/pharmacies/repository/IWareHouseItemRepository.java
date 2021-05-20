package com.schnabel.schnabel.pharmacies.repository;

import com.schnabel.schnabel.pharmacies.model.WareHouseItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * Warehouse item Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "warehouseitem", path = "item")
public interface IWareHouseItemRepository extends JpaRepository<WareHouseItem, Long> {

}


