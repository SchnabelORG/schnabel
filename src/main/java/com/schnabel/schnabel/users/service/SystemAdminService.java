package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.users.dto.SystemAdminDTO;
import com.schnabel.schnabel.users.dto.SystemAdminDTOAssembler;
import com.schnabel.schnabel.users.model.*;
import com.schnabel.schnabel.users.repository.IRoleRepository;
import com.schnabel.schnabel.users.repository.ISystemAdminRepository;

import com.schnabel.schnabel.users.repository.IUserssRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * SystemAdmin service implementation
 */
@Service
public class SystemAdminService extends JpaService<SystemAdmin, Long, ISystemAdminRepository> implements ISystemAdminService
{
    private final PasswordEncoder passwordEncoder;
    private final SystemAdminDTOAssembler systemAdminDTOAssembler;
    private final PagedResourcesAssembler<SystemAdmin> systemAdminPagedResourcesAssembler;
    private final IUserssRepository userssRepository;
    private final IRoleRepository roleRepository;

    @Autowired
    public SystemAdminService(ISystemAdminRepository repository, SystemAdminDTOAssembler systemAdminDTOAssembler, PagedResourcesAssembler<SystemAdmin> systemAdminPagedResourcesAssembler, IUserssRepository userssRepository, IRoleRepository roleRepository)
    {
        super(repository);
        this.systemAdminDTOAssembler = systemAdminDTOAssembler;
        this.systemAdminPagedResourcesAssembler = systemAdminPagedResourcesAssembler;
        this.userssRepository = userssRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Optional<SystemAdminDTO> getDTO(Long id) {
        return get(id).map(systemAdminDTOAssembler::toModel);
    }

    @Override
    public PagedModel<SystemAdminDTO> getAllDTO(Pageable pageable) {
        Page<SystemAdmin> systemAdmins = getAll(pageable);
        return systemAdminPagedResourcesAssembler.toModel(systemAdmins, systemAdminDTOAssembler);
    }

    @Override
    public boolean registerSystemAdmin(String name, String surname, String email, String password, Address address) {
        String pas = passwordEncoder.encode(password);
        SystemAdmin systemAdmin = new SystemAdmin();
        systemAdmin.setName(name);
        systemAdmin.setSurname(surname);
        systemAdmin.setEmail(email);
        systemAdmin.setPassword(pas);
        systemAdmin.setAddress(address);
        systemAdmin.setActivated(false);
        if(repository.findByEmail(email).isPresent())
            return false;
        Optional<SystemAdmin> sya = add(systemAdmin);
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(role);
        UserS user = new UserS(email, pas);
        user.setRoles(roles);
        userssRepository.save(user);
        return true;
    }

    @Override
    public boolean isActive(String email) {
        Optional<SystemAdmin> systemAdmin = repository.findByEmail(email);
        if(!systemAdmin.isPresent())
            return false;
        return true;
    }

    @Override
    public boolean changePassword(String email, String password) {
        Optional<SystemAdmin> systemAdmin = repository.findByEmail(email);
        if(!systemAdmin.isPresent())
            return false;
        String pas = passwordEncoder.encode(password);
        systemAdmin.get().setPassword(pas);
        systemAdmin.get().setActivated(true);
        Optional<UserS> user = userssRepository.findByEmail(email);
        if(!user.isPresent()) {
            return false;
        }
        user.get().setPassword(pas);
        repository.save(systemAdmin.get());
        userssRepository.save(user.get());
        return true;
    }

    @Override
    public Optional<SystemAdminDTO> findByEmail(String email) {
        return repository.findByEmail(email).map(systemAdminDTOAssembler::toModel);
    }
}
