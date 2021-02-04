package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.SystemAdmin;
import org.springframework.data.repository.CrudRepository;

public interface ISystemAdminRepository extends CrudRepository<SystemAdmin, Integer> {
}
