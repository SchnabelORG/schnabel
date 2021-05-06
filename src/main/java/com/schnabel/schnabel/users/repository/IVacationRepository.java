package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.Vacation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * Vacation Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "vacations", path = "vacation")
public interface IVacationRepository extends JpaRepository<Vacation, Long>
{
}
