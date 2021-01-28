package com.schnabel.schnabel.drugs.service;

import java.util.Locale;

import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.repository.IDrugRepository;
import com.schnabel.schnabel.misc.implementations.CrudService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugService extends CrudService<Drug, Integer> implements IDrugService
{

    @Autowired
    public DrugService(IDrugRepository repository)
    {
        super(repository);
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
