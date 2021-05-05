package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * User Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "users", path = "user")
public interface IUserRepository extends JpaRepository<User, Long>
{

}
