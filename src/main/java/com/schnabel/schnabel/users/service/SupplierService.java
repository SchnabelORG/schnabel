package com.schnabel.schnabel.users.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.users.dto.SupplierDTO;
import com.schnabel.schnabel.users.dto.SupplierDTOAssembler;
import com.schnabel.schnabel.users.model.ERole;
import com.schnabel.schnabel.users.model.Role;
import com.schnabel.schnabel.users.model.Supplier;
import com.schnabel.schnabel.users.model.UserS;
import com.schnabel.schnabel.users.repository.IRoleRepository;
import com.schnabel.schnabel.users.repository.ISupplierRepository;

import com.schnabel.schnabel.users.repository.IUserssRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;

    private final SupplierDTOAssembler supplierDTOAssembler;
    private final PagedResourcesAssembler<Supplier> supplierPagedAsm;

    private final IUserssRepository userssRepository;
    private final IRoleRepository roleRepository;

    @Autowired
    public SupplierService(ISupplierRepository repository, SupplierDTOAssembler supplierDTOAssembler, PagedResourcesAssembler<Supplier> supplierPagedAsm, IUserssRepository userssRepository, IRoleRepository roleRepository)
    {
        super(repository);
        this.userssRepository = userssRepository;
        this.roleRepository = roleRepository;
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
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(ERole.ROLE_SUPPLIER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(role);
        UserS user = new UserS(email, encodedPassword);
        user.setRoles(roles);
        userssRepository.save(user);
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

    @Override
    public Optional<SupplierDTO> findByEmail(String email) {
        return repository.findByEmail(email).map(supplierDTOAssembler::toModel);
    }

    @Override
    public boolean isActive(String email) {
        Optional<Supplier> supplier = repository.findByEmail(email);
        if(!supplier.isPresent()){
            return false;
        }
        return supplier.get().isActivated();
    }

    @Override
    public boolean changePassword(String email, String password) {
        Optional<Supplier> supplier = repository.findByEmail(email);
        if(!supplier.isPresent()){
            return false;
        }
        String pas = passwordEncoder.encode(password);
        supplier.get().setPassword(pas);
        supplier.get().setActivated(true);
        repository.save(supplier.get());
        Optional<UserS> user = userssRepository.findByEmail(email);
        if(!user.isPresent()) {
            return false;
        }
        user.get().setPassword(pas);
        userssRepository.save(user.get());
        return true;
    }

    @Override
    public Optional<SupplierDTO> updateSupplier(SupplierDTO dto, String email) {
        Optional<Supplier> supplier = repository.findByEmail(email);
        if(!supplier.isPresent())
            return null;
        supplier.get().setAddress(dto.getAddress());
        supplier.get().setName(dto.getName());
        supplier.get().setSurname(dto.getSurname());
        supplier.get().setFirm(dto.getFirm());

        repository.save(supplier.get());
        return supplier.map(supplierDTOAssembler::toModel);
    }
}
