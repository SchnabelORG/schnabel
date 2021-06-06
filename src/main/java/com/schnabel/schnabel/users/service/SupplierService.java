package com.schnabel.schnabel.users.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.users.dto.SupplierDTO;
import com.schnabel.schnabel.users.dto.SupplierDTOAssembler;
import com.schnabel.schnabel.users.model.Supplier;
import com.schnabel.schnabel.users.repository.ISupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

/**
 * Supplier service implementation
 */
@Service
public class SupplierService extends JpaService<Supplier, Long, ISupplierRepository> implements ISupplierService
{
    private final SupplierDTOAssembler supplierDTOAssembler;
    private final PagedResourcesAssembler<Supplier> supplierPagedAsm;
    
    @Autowired
    public SupplierService(ISupplierRepository repository, SupplierDTOAssembler supplierDTOAssembler, PagedResourcesAssembler<Supplier> supplierPagedAsm)
    {
        super(repository);
        this.supplierDTOAssembler = supplierDTOAssembler;
        this.supplierPagedAsm = supplierPagedAsm;
    }

    @Override
    @Transactional
    public Optional<SupplierDTO> getDTO(Long id) 
    {
        return get(id).map(supplierDTOAssembler::toModel);
    }

    @Override
    @Transactional
    public PagedModel<SupplierDTO> getAllDTO(Pageable pageable) 
    {
        Page<Supplier> suppliers = getAll(pageable);
        return supplierPagedAsm.toModel(suppliers, supplierDTOAssembler);
    }
}
