package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.Supplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Supplier Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "suppliers", path = "supplier")
public interface ISupplierRepository extends JpaRepository<Supplier, Long>
{
}
