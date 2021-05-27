package com.schnabel.schnabel.users.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.dto.DermatologistDTOAssembler;
import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.repository.IDermatologistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Dermatologist service implementation
 */
@Service
public class DermatologistService extends JpaService<Dermatologist, Long, IDermatologistRepository> implements IDermatologistService
{

    private final PasswordEncoder passwordEncoder;
    private final DermatologistDTOAssembler dermatologistDTOAsm;
    private final PagedResourcesAssembler<Dermatologist> dermatologistPagedAsm;

    @Autowired
    public DermatologistService(IDermatologistRepository repository, PagedResourcesAssembler<Dermatologist> dermatologistPagedAsm, DermatologistDTOAssembler dermatologistDTOAsm)
    {
		  super(repository);
          this.dermatologistPagedAsm = dermatologistPagedAsm;
          this.dermatologistDTOAsm = dermatologistDTOAsm;
          this.passwordEncoder = new BCryptPasswordEncoder();
	  }

    @Override
    @Transactional
    public Optional<DermatologistDTO> getDTO(Long id) {
        return get(id)
        .map(dermatologistDTOAsm::toModel);
    }

    @Override
    @Transactional
    public PagedModel<DermatologistDTO> getAllDTO(Pageable pageable) {
        Page<Dermatologist> dermatologists = getAll(pageable);
        return dermatologistPagedAsm.toModel(dermatologists, dermatologistDTOAsm);
    }

    @Override
    public boolean registerDermatologist(String name, String surname, String email, String password, Address address) {
        String encodedPassword = passwordEncoder.encode(password);
        Dermatologist newDermatologist = new Dermatologist(name, surname, email, encodedPassword, address, false );
        Optional<Dermatologist> dermatologist = add(newDermatologist);
        if(dermatologist.isPresent())
            return true;
        return false;
    }


}


