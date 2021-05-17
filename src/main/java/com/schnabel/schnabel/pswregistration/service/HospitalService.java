package com.schnabel.schnabel.pswregistration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schnabel.schnabel.pswregistration.repository.IHospitalRepository;

import java.util.Optional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pswregistration.model.Hospital;

@Service
public class HospitalService extends JpaService<Hospital, String, IHospitalRepository> implements IHospitalService
{
    @Autowired
    public HospitalService(IHospitalRepository repository)
    {
        super(repository);
    }

    @Override
    public Optional<Hospital> add(Hospital hospital)
    {
        if(get(hospital.getApiKey()) == null)
        {
            hospital.setApiKey(("api" + hospital.getName() + "1234").replace(' ', '_'));
            repository.save(hospital);
        }
        return repository.findById(hospital.getId());
    }
}
