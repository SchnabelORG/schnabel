package com.schnabel.schnabel.drugs.service;

import java.util.List;

import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.repository.IDrugRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugService implements IDrugService
{
    private IDrugRepository _drugRepository;

    @Autowired
    public DrugService(IDrugRepository drugRepository)
    {
        this._drugRepository = drugRepository;
    }

	@Override
    public boolean add(Drug drug)
    {
        if(get(drug.getId()) != null) return false;
        _drugRepository.save(drug);
        return true;
	}

	@Override
    public boolean remove(int id)
    {
        Drug drug = get(id);
        if(drug == null) return false;
        _drugRepository.delete(drug);
        return true;
	}

	@Override
    public boolean update(Drug drug)
    {
        if(get(drug.getId()) == null) return false;
        _drugRepository.save(drug);
        return true;
	}

	@Override
    public Drug get(int id)
    {
		return _drugRepository.findById(id).orElse(null);
	}

	@Override
    public List<Drug> getAll()
    {
        return (List<Drug>) _drugRepository.findAll();
	}
    
}
