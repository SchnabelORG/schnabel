package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.users.model.User;
import com.schnabel.schnabel.users.repository.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User service implementation
 */
@Service
public class UserService extends CrudService<User, Long> implements IUserService
{
    @Autowired
    public UserService(IUserRepository repository)
    {
        super(repository);
    }
}
