package com.schnabel.schnabel.users.service;

import java.util.Optional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.users.dto.FreePharmacistLookupRequest;
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

/**
 * Pharmacist service implementation
 */
@Service
public class PharmacistService extends JpaService<Pharmacist, Long, IPharmacistRepository> implements IPharmacistService
{

    private final PharmacistDTOAssembler dtoAsm;
    private final PagedResourcesAssembler<Pharmacist> pageAsm;

    @Autowired
    public PharmacistService(IPharmacistRepository repository, PharmacistDTOAssembler pharmacistDTOAssembler, PagedResourcesAssembler<Pharmacist> pharmacistPageAsm)
    {
        super(repository);
        this.dtoAsm = pharmacistDTOAssembler;
        this.pageAsm = pharmacistPageAsm;
    }

    @Override
    public PagedModel<PharmacistDTO> findByPharmacy(Long pharmacyId, Pageable pageable) {
        Page<Pharmacist> pharmacists = repository.findByPharmacyId(pharmacyId, pageable);
        return pageAsm.toModel(pharmacists, dtoAsm);
    }

    @Override
    public PagedModel<PharmacistDTO> findFreeByPharmacy(FreePharmacistLookupRequest req, Pageable pageable) {
        Page<Pharmacist> pharmacists = repository.findFreeByPharmacy(req.getPharmacyId(), req.getPeriod().getStartTime(), req.getPeriod().getEndTime(), pageable);
        return pageAsm.toModel(pharmacists, dtoAsm);
    }

    @Override
    public PagedModel<PharmacistDTO> findAllDTO(Pageable pageable) {
        Page<Pharmacist> pharmacists = getAll(pageable);
        return pageAsm.toModel(pharmacists, dtoAsm);
    }

    @Override
    public Optional<PharmacistDTO> findbyIdDTO(Long id) {
        return get(id).map(dtoAsm::toModel);
    }

    @Override
    public Optional<PharmacistDTO> updateDTO(Pharmacist pharmacist) {
        return update(pharmacist) ?
            findbyIdDTO(pharmacist.getId())
            : Optional.empty();
    }


}
