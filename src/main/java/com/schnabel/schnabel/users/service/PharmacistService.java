package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.dto.PharmacistDTOAssembler;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.repository.IPharmacistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Pharmacist service implementation
 */
@Service
public class PharmacistService extends JpaService<Pharmacist, Long, IPharmacistRepository> implements IPharmacistService
{

    private final PharmacistDTOAssembler pharmacistDTOAssembler;
    private final PagedResourcesAssembler<Pharmacist> pharmacistPagedResourcesAssembler;

    @Autowired
    public PharmacistService(IPharmacistRepository repository, PharmacistDTOAssembler pharmacistDTOAssembler, PagedResourcesAssembler<Pharmacist> pharmacistPagedResourcesAssembler)
    {

        super(repository);
        this.pharmacistDTOAssembler = pharmacistDTOAssembler;
        this.pharmacistPagedResourcesAssembler = pharmacistPagedResourcesAssembler;
    }


    @Override
    @Transactional
    public Optional<PharmacistDTO> getDTO(Long id) {
        return get(id).map(pharmacistDTOAssembler::toModel);
    }

    @Override
    @Transactional
    public PagedModel<PharmacistDTO> getAllDTO(Pageable pageable) {

        Page<Pharmacist> pharmacists = getAll(pageable);
        return pharmacistPagedResourcesAssembler.toModel(pharmacists, pharmacistDTOAssembler);
    }
}
