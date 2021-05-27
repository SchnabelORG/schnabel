package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.users.dto.SystemAdminDTO;
import com.schnabel.schnabel.users.model.SystemAdmin;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;

/**
 * SystemAdmin service interface
 */
public interface ISystemAdminService extends IJpaService<SystemAdmin, Long>
{
    Optional<SystemAdminDTO> getDTO(Long id);
    PagedModel<SystemAdminDTO> getAllDTO(Pageable pageable);
    boolean registerSystemAdmin(String name, String surname, String email, String password, Address address);

}
