package com.schnabel.schnabel.pswregistration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.schnabel.schnabel.pswregistration.repository.IHospitalRepository;
import com.schnabel.schnabel.pswregistration.model.Hospital;

@Service
public class HospitalService implements IHospitalService{

    private final IHospitalRepository _hospitalRepository;

    @Autowired
    public HospitalService(IHospitalRepository repository)
    {
        this._hospitalRepository = repository;
    }

    @Override
    public boolean add(Hospital hospital)
    {
        if(get(hospital.getApiKey()) == null)
        {
            hospital.setApiKey(("api" + hospital.getName() + "1234").replace(' ', '_'));
            _hospitalRepository.save(hospital);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(String apiKey)
    {
        Hospital h = get(apiKey);
        if(h == null)
        {
            return false;
        }
        _hospitalRepository.delete(h);
        return true;
    }

    @Override
    public boolean update(Hospital hospital)
    {
        if(get(hospital.getApiKey()) == null) return false;
        _hospitalRepository.save(hospital);
        return true;
    }

    @Override
    public Hospital get(String apiKey)
    {
        return _hospitalRepository.findById(apiKey).orElse(null);
    }

    @Override
    public List<Hospital> getAll()
    {
        return (List<Hospital>) _hospitalRepository.findAll();
    }
}
