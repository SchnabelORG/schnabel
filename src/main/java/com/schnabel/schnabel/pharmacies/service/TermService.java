package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.pharmacies.model.Term;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class TermService extends CrudService<Term, Integer> implements ITermService {
    public TermService(CrudRepository<Term, Integer> repository) {
        super(repository);
    }
}
