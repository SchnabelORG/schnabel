package com.schnabel.schnabel.pharmacies.repository;

import java.util.Optional;

import com.schnabel.schnabel.pharmacies.model.WareHouseItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * Warehouse item Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "warehouseitems", path = "warehouseitem")
public interface IWareHouseItemRepository extends JpaRepository<WareHouseItem, Long> 
{
    Page<WareHouseItem> findByPharmacyId(Long pharmacyId, Pageable pageable);
    Optional<WareHouseItem> findByPharmacyIdAndDrugId(Long pharmacyId, Long drugId);
}
