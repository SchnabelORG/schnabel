package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.User;

import org.springframework.data.repository.CrudRepository;

/**
 * User CRUD repository interface
 */
public interface IUserRepository extends CrudRepository<User, Long>
{
    
}
