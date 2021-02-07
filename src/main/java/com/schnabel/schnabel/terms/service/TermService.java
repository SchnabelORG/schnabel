package com.schnabel.schnabel.terms.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.terms.model.Term;
import com.schnabel.schnabel.terms.repository.ITermRepository;
import org.springframework.stereotype.Service;

/**
 * Term service implementation
 */
@Service
public class TermService extends CrudService<Term, Long> implements ITermService
{

    public TermService(ITermRepository repository)
    {
        super(repository);
    }

}
