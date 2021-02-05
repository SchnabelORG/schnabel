package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.users.model.Supplier;
import org.springframework.data.repository.CrudRepository;

public class SupplierService extends CrudService<Supplier, Long> implements ISupplierService
{
    public SupplierService(CrudRepository<Supplier, Long> repository) {
        super(repository);
    }
}
