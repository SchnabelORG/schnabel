package com.schnabel.schnabel.email.service;

import com.schnabel.schnabel.email.model.Activation;
import com.schnabel.schnabel.email.repository.IActivationRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivationService extends JpaService<Activation, String, IActivationRepository> implements IActivationService {

    @Autowired
    public ActivationService(IActivationRepository repository) {
        super(repository);
    }
    
}
