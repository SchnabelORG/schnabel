package com.schnabel.schnabel.security.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.schnabel.schnabel.users.model.User;
import com.schnabel.schnabel.users.model.UserS;
import com.schnabel.schnabel.users.repository.IUserRepository;

import com.schnabel.schnabel.users.repository.IUserssRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * SchnabelUserDetails service layer
 */
@Service
public class SchnabelUserDetailsService implements ISchnabelUserDetailsService {
    
    private final IUserssRepository repository;

    @Autowired
    public SchnabelUserDetailsService(IUserssRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserS> user = repository.findByEmail(email);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }

        return SchnabelUserDetails.build(user.get());
    }

    

}
