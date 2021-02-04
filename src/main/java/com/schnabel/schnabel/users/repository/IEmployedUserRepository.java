package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.EmployedUser;
import org.springframework.data.repository.CrudRepository;

public interface IEmployedUserRepository extends CrudRepository<EmployedUser, Integer>
{
}
