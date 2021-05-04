package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.PharmacyAdmin;

import org.springframework.data.repository.CrudRepository;

/**
 * PharmacyAdmin CRUD repository interface
 */
public interface IPharmacyAdminRepository extends CrudRepository<PharmacyAdmin, Long>
{
}

