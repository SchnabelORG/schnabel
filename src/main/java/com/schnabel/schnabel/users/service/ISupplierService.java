package com.schnabel.schnabel.users.service;

import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.users.dto.SupplierDTO;
import com.schnabel.schnabel.users.model.Supplier;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

/**
 * Supplier service interface
 */
public interface ISupplierService extends IJpaService<Supplier, Long>
{
    boolean registerSupplier(String name, String surname, String email, String password, Address address, String company );
    Optional<SupplierDTO> getDTO(Long id);
    PagedModel<SupplierDTO> getAllDTO(Pageable pageable);
}
