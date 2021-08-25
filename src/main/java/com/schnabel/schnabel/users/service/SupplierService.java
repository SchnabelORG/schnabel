package com.schnabel.schnabel.users.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.dto.SupplierDTO;
import com.schnabel.schnabel.users.dto.SupplierDTOAssembler;
import com.schnabel.schnabel.users.model.Supplier;
import com.schnabel.schnabel.users.repository.ISupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Supplier service implementation
 */
@Service
public class SupplierService extends JpaService<Supplier, Long, ISupplierRepository> implements ISupplierService
{

    private final PasswordEncoder passwordEncoder;

    private final SupplierDTOAssembler supplierDTOAssembler;
    private final PagedResourcesAssembler<Supplier> supplierPagedAsm;
    
    @Autowired
    public SupplierService(ISupplierRepository repository, SupplierDTOAssembler supplierDTOAssembler, PagedResourcesAssembler<Supplier> supplierPagedAsm)
    {
        super(repository);
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.supplierDTOAssembler = supplierDTOAssembler;
        this.supplierPagedAsm = supplierPagedAsm;
    }

    @Override
    public boolean registerSupplier(String name, String surname, String email, String password, Address address,
                                   String company) {
        String encodedPassword = passwordEncoder.encode(password);
        Supplier newSupplier= new Supplier(name, surname, email, encodedPassword, address, false, company);
        Optional<Supplier> supplier = add(newSupplier);
        if(supplier.isPresent()) {
            //Todo(Marko): Send activation email after supplier registration
            //return sendActivationEmail(email);
            return true;
        }
        return false;
       
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
