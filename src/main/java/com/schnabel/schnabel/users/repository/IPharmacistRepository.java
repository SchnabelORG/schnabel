package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.Pharmacist;

import org.springframework.data.repository.CrudRepository;

/**
 * Pharmacist CRUD repository interface
 */
public interface IPharmacistRepository extends CrudRepository<Pharmacist, Long>
{

}
