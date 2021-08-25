package com.schnabel.schnabel.drugs.service;


import java.util.Optional;

import javax.transaction.Transactional;

import com.schnabel.schnabel.drugs.dto.AvailabilityRequestDTO;
import com.schnabel.schnabel.drugs.dto.AvailabilityRequestDTOAssembler;
import com.schnabel.schnabel.drugs.model.AvailabilityRequest;
import com.schnabel.schnabel.drugs.repository.IAvailabilityRequestRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

/**
 * AvailabilityRequest JPA service implementation
 */
@Service
public class AvailabilityRequestService extends JpaService<AvailabilityRequest, Long, IAvailabilityRequestRepository> implements IAvailabilityRequestService {
    
    private final AvailabilityRequestDTOAssembler dtoAsm;
    private final PagedResourcesAssembler<AvailabilityRequest> pageAsm;

    @Autowired
    public AvailabilityRequestService(IAvailabilityRequestRepository repository, AvailabilityRequestDTOAssembler dtoAsm, PagedResourcesAssembler<AvailabilityRequest> pageAsm)
    {
        super(repository);
        this.dtoAsm = dtoAsm;
        this.pageAsm = pageAsm;
    }

    @Override
    @Transactional
    public Optional<AvailabilityRequestDTO> findByIdDTO(Long id)
    {
        return get(id).map(dtoAsm::toModel);
    }

    @Override
    @Transactional
    public PagedModel<AvailabilityRequestDTO> findByPharmacyId(Long id, Pageable pageable)
    {
        Page<AvailabilityRequest> req = repository.findByPharmacyId(id, pageable);
        return pageAsm.toModel(req, dtoAsm);
    }

}
