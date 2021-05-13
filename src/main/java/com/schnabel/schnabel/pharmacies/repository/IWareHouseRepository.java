package com.schnabel.schnabel.pharmacies.repository;

import com.schnabel.schnabel.pharmacies.model.WareHouse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Warehouse Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "warehouse", path = "warehouse")
public interface IWareHouseRepository  extends JpaRepository<WareHouse, Long>{


}
