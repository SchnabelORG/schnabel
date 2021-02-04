package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.pharmacies.model.WorksIn;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class WorksInService extends CrudService<WorksIn, Integer> implements IWorksInService {
    public WorksInService(CrudRepository<WorksIn, Integer> repository) {
        super(repository);
    }
}
