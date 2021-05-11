package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.users.model.SystemAdmin;
import com.schnabel.schnabel.users.repository.ISystemAdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SystemAdmin service implementation
 */
@Service
public class SystemAdminService extends JpaService<SystemAdmin, Long> implements ISystemAdminService
{
    @Autowired
    public SystemAdminService(ISystemAdminRepository repository)
    {
        super(repository);
    }
}
