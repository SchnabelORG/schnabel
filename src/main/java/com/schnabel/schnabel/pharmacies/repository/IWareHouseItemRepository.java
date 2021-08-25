package com.schnabel.schnabel.pharmacies.repository;

import java.util.List;
import java.util.Optional;

import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * Warehouse item Jpa repository interface
 */
public interface IWareHouseItemRepository extends JpaRepository<WareHouseItem, Long> 
{
    Page<WareHouseItem> findByPharmacyId(Long pharmacyId, Pageable pageable);
    @Query("SELECT w FROM WareHouseItem w WHERE w.pharmacy.id = ?2 and w.drug.id = ?1 ")
    Optional<WareHouseItem> findByPharmacyIdAndDrugId(Long drugId, Long pharmacyId);
    //Optional<WareHouseItem> findByPharmacyIdAndDrugId(Long pharmacyId, Long drugId);

}
