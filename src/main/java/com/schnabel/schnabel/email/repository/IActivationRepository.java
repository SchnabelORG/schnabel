package com.schnabel.schnabel.email.repository;

import com.schnabel.schnabel.email.model.Activation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IActivationRepository extends JpaRepository<Activation, String> {
    
}
