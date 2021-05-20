package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.Shift;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * Shift Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "shifts", path = "shift")
public interface IShiftRepository extends JpaRepository<Shift, Long>
{
}
