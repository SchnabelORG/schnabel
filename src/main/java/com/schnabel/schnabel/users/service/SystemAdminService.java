package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.dto.SystemAdminDTO;
import com.schnabel.schnabel.users.dto.SystemAdminDTOAssembler;
import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.model.SystemAdmin;
import com.schnabel.schnabel.users.repository.ISystemAdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * SystemAdmin service implementation
 */
@Service
public class SystemAdminService extends JpaService<SystemAdmin, Long, ISystemAdminRepository> implements ISystemAdminService
{
    private final PasswordEncoder passwordEncoder;
    private final SystemAdminDTOAssembler systemAdminDTOAssembler;
    private final PagedResourcesAssembler<SystemAdmin> systemAdminPagedResourcesAssembler;
    @Autowired
    public SystemAdminService(ISystemAdminRepository repository, SystemAdminDTOAssembler systemAdminDTOAssembler, PagedResourcesAssembler<SystemAdmin> systemAdminPagedResourcesAssembler)
    {
        super(repository);
        this.systemAdminDTOAssembler = systemAdminDTOAssembler;
        this.systemAdminPagedResourcesAssembler = systemAdminPagedResourcesAssembler;
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
        String encodedPassword = passwordEncoder.encode(password);
        SystemAdmin newSystemAdmin = new SystemAdmin(name, surname, email, encodedPassword, address, false );
        Optional<SystemAdmin> systemAdmin = add(newSystemAdmin);
        if(systemAdmin.isPresent())
            return true;
        return false;
    }
}
