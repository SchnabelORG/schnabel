package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.users.model.SystemAdmin;
import org.springframework.data.repository.CrudRepository;

public class SystemAdminService extends CrudService<SystemAdmin, Integer> implements ISystemAdminService {
    public SystemAdminService(CrudRepository<SystemAdmin, Integer> repository) {
        super(repository);
    }
}
