package com.schnabel.schnabel.pswregistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HospitalService implements IHospitalService{
    @Autowired
    private HospitalRepository repository;

    @Override
    public boolean add(Hospital hospital)
    {
        if(get(hospital.getName()) == null)
        {
            hospital.setApiKey(("api" + hospital.getName() + "1234").replace(' ', '_'));
            repository.save(hospital);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(String name)
    {
        Hospital h = get(name);
        if(h == null)
        {
            return false;
        }
        repository.delete(h);
        return true;
    }

    @Override
    public boolean update(Hospital hospital)
    {
        if(get(hospital.getName()) == null) return false;
        repository.save(hospital);
        return true;
    }

    @Override
    public Hospital get(String name)
    {
        return repository.findById(name).orElse(null);
    }

    @Override
    public List<Hospital> getAll()
    {
        return (List<Hospital>) repository.findAll();
    }
}
