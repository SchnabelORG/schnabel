package com.schnabel.schnabel.pharmacies.repository;

import com.schnabel.schnabel.pharmacies.model.WareHouseItem;

import org.springframework.data.repository.CrudRepository;

/**
 * WareHouseItem CRUD repository interface
 */
public interface IWareHouseItemRepository extends CrudRepository<WareHouseItem, Long>
{
    
}
