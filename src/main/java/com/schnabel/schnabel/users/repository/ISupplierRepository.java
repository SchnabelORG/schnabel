package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.Supplier;

import org.springframework.data.repository.CrudRepository;

/**
 * Supplier CRUD repository interface
 */
public interface ISupplierRepository extends CrudRepository<Supplier, Long>
{
    
}
