package com.schnabel.schnabel.pswregistration.repository;

import com.schnabel.schnabel.pswregistration.model.Hospital;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHospitalRepository extends JpaRepository<Hospital, String>
{
    
}
