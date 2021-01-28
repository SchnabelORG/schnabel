package com.schnabel.schnabel.drugs.service;

import java.util.Locale;

import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.repository.IDrugRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugService implements IDrugService
{
    private final IDrugRepository drugRepository;

    @Autowired
    public DrugService(IDrugRepository drugRepository)
    {
        this.drugRepository = drugRepository;
    }

	@Override
    public boolean add(Drug drug)
    {
        if(get(drug.getId()) != null) return false;
        drugRepository.save(drug);
        return true;
	}

	@Override
    public boolean remove(Integer id)
    {
        Drug drug = get(id);
        if(drug == null) return false;
        drugRepository.delete(drug);
        return true;
	}

	@Override
    public boolean update(Drug drug)
    {
        if(get(drug.getId()) == null) return false;
        drugRepository.save(drug);
        return true;
	}

	@Override
    public Drug get(Integer id)
    {
		return drugRepository.findById(id).orElse(null);
	}

	@Override
    public Iterable<Drug> getAll()
    {
        return drugRepository.findAll();
	}

    @Override
    public boolean getByName(String name) {
        Iterable<Drug> drugs = getAll();
        for (Drug drug: drugs) {
            if(drug.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT))) return true;
        }
        return false;
    }

}
