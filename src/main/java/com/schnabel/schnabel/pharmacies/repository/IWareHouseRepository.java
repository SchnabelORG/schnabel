package com.schnabel.schnabel.pharmacies.repository;

import com.schnabel.schnabel.pharmacies.model.WareHouse;

import org.springframework.data.repository.CrudRepository;

/**
 * WareHouse CRUD repository interface
 */
public interface IWareHouseRepository extends CrudRepository<WareHouse, Long>
{
    
}
