package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.Dermatologist;

import org.springframework.data.repository.CrudRepository;

/**
 * Dermatologist CRUD repository interface
 */
public interface IDermatologistRepository extends CrudRepository<Dermatologist, Long>
{

}
