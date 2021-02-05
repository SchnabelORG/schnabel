package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.pharmacies.model.Term;
import com.schnabel.schnabel.pharmacies.repository.ITermRepository;

import org.springframework.stereotype.Service;

@Service
public class TermService extends CrudService<Term, Long> implements ITermService
{
    public TermService(ITermRepository repository)
    {
        super(repository);
    }
}
