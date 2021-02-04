package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.users.model.EmployedUser;
import com.schnabel.schnabel.users.repository.IEmployedUserRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployedUserService extends CrudService<EmployedUser, Integer> implements IEmployedUserService
{

    public EmployedUserService(IEmployedUserRepository employedUserRepository)
    {
        super(employedUserRepository);
    }

}
